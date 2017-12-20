package com.c3.pcust30.fragment.child.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.c3.library.weight.overlay.dialog.BottomMenuDialog
import com.c3.library.weight.overlay.model.MenuModel
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.adapter.ClientListAdapter
import com.c3.pcust30.base.frag.LoadRefreshListFragment
import com.c3.pcust30.data.info.UserInfo
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.ClientDataListRsp
import com.c3.pcust30.http.config.CLIENT_DATA_LIST_CODE
import com.c3.pcust30.http.config.CLIENT_DATA_LIST_LEVEL_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.c3.pcust30.top.bindDataWithSetShowType
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.view_date_list_with_search.*

/**
 * 作者： LYJ
 * 功能： 客户管理界面
 * 创建日期： 2017/12/9
 */
class ClientManagementFragment : LoadRefreshListFragment(), BaseQuickAdapter.OnItemClickListener {

    private val clientList: MutableList<ClientDataListRsp.ClientInfo> by lazy { mutableListOf<ClientDataListRsp.ClientInfo>() }
    private val clientDataAdapter: ClientListAdapter by lazy { ClientListAdapter(clientList) }
    //菜单选项
    private val menuDialog: BottomMenuDialog by lazy {
        BottomMenuDialog(mContext, listOf(
                MenuModel("修改", R.drawable.menu_change_icon),
                MenuModel("电话", R.drawable.menu_call_icon),
                MenuModel("会面", R.drawable.menu_meet_icon)))
    }

    override fun setFragmentView(): Int = R.layout.fragment_client_management

    override fun setTitleText(): CharSequence = resources.getString(R.string.frag_title_client)

    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        bindRefreshWithLoadMoreListener(refreshGroup)
        bindDataWithSetShowType(dataView, LinearLayoutManager(mContext), clientDataAdapter)
        filter1 = arguments.getString("type")
        filter10 = arguments.getString("label")
        clientInfoLevelFilter = arguments.getString("level")
        clientDataAdapter.onItemClickListener = this//设置item点击事件
    }

    /**
     * 点击事件
     */
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        ShowHint.hint(mContext, "$position")
        menuDialog.show()
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        getDataFromServer()//获取客户列表数据
    }

    private var filter1: String? = null//过滤条件一(跟进状态)
    private var filter2: String? = null//过滤条件二(客户来源)
    private var filter3: String? = null//过滤条件三(资产规模)
    private var filter4: String? = null//过滤条件四(输入的条件)
    private var filter5: String? = null//过滤条件五(星级)
    private var filter6: String? = null//过滤条件六(通话次数)
    private var filter7: String? = null//过滤条件七(通话时长)
    private var filter8: String? = null//过滤条件八(最近联系时间)
    private var filter9: String? = null//过滤条件九(通过年龄段搜索)
    private var filter10: String? = null//过滤条件十（通过标签筛选列表）
    private var clientInfoLevelFilter: String? = null//客户评级分类条件
    /**
     * 请求后台服务
     */
    override fun getDataFromServer(loadType: Int, loadPage: Int) {
        super.getDataFromServer(loadType, loadPage)
        showLoading()//展示弹窗
        val requestJson: String?
        val requestBody = TradingRequest()
                .addHeader(LOGIN_USER_NAME, UserInfo.userCode)
                .addHeader(TODAY_TASK_DATA_PAGE_NO, loadPage.toString())
                .addHeader(TODAY_TASK_DATA_PAGE_SIZE, pageDataCount)
                .addBody(LOGIN_USER_CODE, UserInfo.userCode)
                .addBody(LOGIN_ORG_CODE, UserInfo.orgCode)
        if (clientInfoLevelFilter.isNullOrEmpty()) {
            requestJson = getJson(requestBody.addHeader(SERVICE_CODE, CLIENT_DATA_LIST_CODE)
                    .addBody("followingstatus", filter1 ?: "")
                    .addBody("accessway", filter2 ?: "")
                    .addBody("cusinvestscale", filter3 ?: "")
                    .addBody("inputValue", filter4 ?: "")
                    .addBody("star", filter5 ?: "")
                    .addBody("talkcount", filter6 ?: "")
                    .addBody("talktime", filter7 ?: "")
                    .addBody("latestdate", filter8 ?: "")
                    .addBody("groupage", filter9 ?: "")
                    .addBody("labelid", filter10 ?: ""))
        } else {//客户信息有效无效列表数据列表请求
            requestJson = getJson(requestBody.addHeader(SERVICE_CODE, CLIENT_DATA_LIST_LEVEL_CODE)
                    .addBody("yorn", clientInfoLevelFilter!!))
        }
        TradingTool.commitTrading(requestJson)
                .doFinally {
                    hideLoading()
                    if (refreshGroup.isRefreshing) refreshGroup.finishRefresh()
                    if (!refreshGroup.isLoadmoreFinished) refreshGroup.finishLoadmore()
                }
                .bindToLifecycle(this)
                .subscribe({ clientJson -> getResponse(clientJson, loadType) },
                        { error -> Logger.t(TAG).w(error.message) })
    }

    /**
     * 请求结果
     */
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        val clientRsp = getResultBodyWithHeader(result, object : TypeToken<TradingResponse<ClientDataListRsp>>() {})
        getResultBody(clientRsp, { _ ->
            val dataCount = clientRsp.header!!.counts ?: "0"
            loadDataToListView(refreshGroup, dataCount, tag, { clientList.clear() },
                    {
                        parseResult(dataCount, result, object : TypeToken<TradingResponse<ClientDataListRsp.ClientEntity>>() {},
                                object : TypeToken<TradingResponse<ClientDataListRsp.ClientArray>>() {},
                                { entityBody -> clientList.add(entityBody.PcustindiinfoView!!) },
                                { arrayBody -> clientList.addAll(arrayBody.PcustindiinfoView!!) })
                        clientDataAdapter.notifyDataSetChanged()
                    }, { --page })

        })
    }
}
