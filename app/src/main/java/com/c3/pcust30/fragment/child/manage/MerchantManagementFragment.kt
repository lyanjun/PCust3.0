package com.c3.pcust30.fragment.child.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.c3.library.weight.overlay.dialog.BottomMenuDialog
import com.c3.library.weight.overlay.listener.OnMenuSelectedListener
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.adapter.MerchantListAdapter
import com.c3.pcust30.base.frag.LoadRefreshListFragment
import com.c3.pcust30.data.info.UserInfo
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.MerchantDataListRsp
import com.c3.pcust30.data.other.MERCHANT_MENU
import com.c3.pcust30.http.config.MERCHANT_DATA_LIST_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.c3.pcust30.top.bindDataWithSetShowType
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.view_date_list_with_search.*


/**
 * 作者： LYJ
 * 功能： 商户管理界面
 * 创建日期： 2017/12/5
 */
class MerchantManagementFragment : LoadRefreshListFragment(), OnRefreshListener, OnLoadmoreListener,
        BaseQuickAdapter.OnItemClickListener, OnMenuSelectedListener {


    private val merChantList: MutableList<MerchantDataListRsp.MerchantInfo> by lazy { mutableListOf<MerchantDataListRsp.MerchantInfo>() }//数据
    private val merChantAdapter: MerchantListAdapter by lazy { MerchantListAdapter(merChantList) }
    //菜单选项
    private var menuDialog: BottomMenuDialog? = null

    override fun setFragmentView(): Int = R.layout.fragment_merchant_management

    override fun setTitleText(): CharSequence = resources.getString(R.string.frag_title_mer_manager)
    /**
     * 初始化设置
     */
    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        bindRefreshWithLoadMoreListener(refreshGroup)//设置监听
        bindDataWithSetShowType(dataView, LinearLayoutManager(mContext), merChantAdapter)//绑定数据
        merChantAdapter.onItemClickListener = this//item点击事件
    }

    /**
     * item点击事件
     */
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
//        ShowHint.hint(mContext,"$position")
        menuDialog = BottomMenuDialog(mContext, MERCHANT_MENU)
        menuDialog!!.setOnMenuSelectedListener(this)
        menuDialog!!.show()
    }

    /**
     * 菜单选择结果
     */
    override fun onMenuSelected(menuType: String) {
        ShowHint.warn(mContext, menuType)
    }

    /**
     * 动画结束后
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        getDataFromServer()//初始化
    }

    /************************************************************************************
     * 过滤条件(筛选条件)
     ************************************************************************************/
    private var filter1: String? = null
    private var filter2: String? = null
    private var filter3: String? = null
    private var filter4: String? = null

    /**
     * 获取商户列表
     */
    override fun getDataFromServer(loadType: Int, loadPage: Int) {
        super.getDataFromServer(loadType, loadPage)
        showLoading()//显示等待弹窗
        val requestJson = getJson(TradingRequest()
                .addHeader(SERVICE_CODE, MERCHANT_DATA_LIST_CODE)
                .addHeader(LOGIN_USER_NAME, UserInfo.userCode)
                .addHeader(TODAY_TASK_DATA_PAGE_NO, loadPage.toString())
                .addHeader(TODAY_TASK_DATA_PAGE_SIZE, pageDataCount)
                .addBody(LOGIN_USER_CODE, UserInfo.userCode)
                .addBody(LOGIN_ORG_CODE, UserInfo.orgCode)
                .addBody("flag", "query")
                //最近联系时间
                .addBody("latestdat", filter1 ?: "")
                //通话时长
                .addBody("talktime", filter2 ?: "")
                //通话次数
                .addBody("talkcount", filter3 ?: "")
                //查询条件
                .addBody("nameOrtel", filter4 ?: ""))
        Logger.t(TAG).json(requestJson)
        TradingTool.commitTrading(requestJson)
                .doFinally {
                    hideLoading()
                    if (refreshGroup.isRefreshing) refreshGroup.finishRefresh()
                    if (!refreshGroup.isLoadmoreFinished) refreshGroup.finishLoadmore()
                }
                .bindToLifecycle(this)
                .subscribe({ merchantJson -> getResponse(merchantJson, loadType) },
                        { error -> Logger.t(TAG).w(error.message) })
    }

    /**
     * 处理返回结果
     */
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        val infoDataRsp = getResultBodyWithHeader(result, object : TypeToken<TradingResponse<MerchantDataListRsp>>() {})//解析结果
        getResultBody(infoDataRsp, { bodyEntity ->
            val merCounts = bodyEntity.visitcount?.visitcount//商户数量
            //加载数据
            loadDataToListView(refreshGroup, merCounts, tag, { merChantList.clear() },
                    {
                        parseResult(merCounts!!, result, object : TypeToken<TradingResponse<MerchantDataListRsp.MerchantEntity>>() {},
                                object : TypeToken<TradingResponse<MerchantDataListRsp.MerchantArray>>() {},
                                { entityBody -> merChantList.add(entityBody.mer!!) },
                                { arrayBody -> merChantList.addAll(arrayBody.mer!!) })
                        merChantAdapter.notifyDataSetChanged()
                    }, { --page })
        })
    }
}