package com.c3.pcust30.fragment.child.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.ViewGroup
import com.c3.pcust30.R
import com.c3.pcust30.adapter.MerchantListAdapter
import com.c3.pcust30.base.frag.BaseFragment
import com.c3.pcust30.data.info.UserInfo
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.MerchantDataListRsp
import com.c3.pcust30.http.config.MERCHANT_DATA_LIST_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.c3.pcust30.top.LOAD_MORE
import com.c3.pcust30.top.LOAD_NONE
import com.c3.pcust30.top.LOAD_REFRESH
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.fragment_merchant_management.*


/**
 * 作者： LYJ
 * 功能： 商户管理界面
 * 创建日期： 2017/12/5
 */
class MerchantManagementFragment : BaseFragment(), OnRefreshListener, OnLoadmoreListener {
    private val pageDataCount = "8"//每页请求的数据数量
    private var page = 1//请求的页数
    private val merChantList: MutableList<MerchantDataListRsp.MerchantInfo> = mutableListOf()//数据
    private val merChantAdapter: MerchantListAdapter by lazy { MerchantListAdapter(R.layout.item_merchant_detail, merChantList) }

    override fun setFragmentView(): Int = R.layout.fragment_merchant_management

    override fun setTitleText(): CharSequence = resources.getString(R.string.frag_title_manager)
    /**
     * 初始化设置
     */
    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        refreshGroup.setOnRefreshListener(this)
        refreshGroup.setOnLoadmoreListener(this)
        merChantAdapter.setEmptyView(R.layout.view_data_empty, merChantView.parent as ViewGroup)
        merChantView.layoutManager = LinearLayoutManager(mContext)
        merChantView.setHasFixedSize(true)
        merChantView.adapter = merChantAdapter//设置适配器
    }

    /**
     * 动画结束后
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        getMerchantListFromNet(LOAD_NONE, page)//初始化
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
    private fun getMerchantListFromNet(loadType: Int, pageNum: Int) {
        page = pageNum
        showLoading()//显示等待弹窗
        val requestJson = getJson(TradingRequest()
                .addHeader(SERVICE_CODE, MERCHANT_DATA_LIST_CODE)
                .addHeader(LOGIN_USER_NAME, UserInfo.userCode)
                .addHeader(TODAY_TASK_DATA_PAGE_NO, pageNum.toString())
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
     * 下拉刷新
     */
    override fun onRefresh(refreshlayout: RefreshLayout?) {
        getMerchantListFromNet(LOAD_REFRESH, 1)
    }

    /**
     * 上拉加载
     */
    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        getMerchantListFromNet(LOAD_MORE, ++page)
    }

    /**
     * 处理返回结果
     */
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        val infoDataType = object : TypeToken<TradingResponse<MerchantDataListRsp>>() {}.type//解析类型
        val infoDataRsp = Gson().fromJson<TradingResponse<MerchantDataListRsp>>(result, infoDataType)//解析结果
        getResultBody(infoDataRsp, { bodyEntity ->
            val merCounts = bodyEntity.visitcount?.visitcount//商户数量
            //加载数据
            loadDataToListView(refreshGroup, merCounts, tag,
                    { merChantList.clear() }, { showDateView(merCounts!!, result) }, { --page })
        })
    }

    /**
     * 显示数据
     */
    private fun showDateView(dataCount: String, resultJson: String) {
        if (TextUtils.equals(dataCount, "1")) {//按对象解析
            val merChantEntity = object : TypeToken<TradingResponse<MerchantDataListRsp.MerchantEntity>>() {}.type//解析类型
            val merEntity = Gson().fromJson<TradingResponse<MerchantDataListRsp.MerchantEntity>>(resultJson, merChantEntity)//解析结果
            merChantList.add(merEntity.body!!.mer!!)
        } else {//按数组解析
            val merChantArray = object : TypeToken<TradingResponse<MerchantDataListRsp.MerchantArray>>() {}.type//解析类型
            val merArray = Gson().fromJson<TradingResponse<MerchantDataListRsp.MerchantArray>>(resultJson, merChantArray)//解析结果
            merChantList.addAll(merArray.body!!.mer!!)
        }
        merChantAdapter.notifyDataSetChanged()
    }
}