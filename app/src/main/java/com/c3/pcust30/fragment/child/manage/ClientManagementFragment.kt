package com.c3.pcust30.fragment.child.manage

import android.os.Bundle
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.LoadRefreshListFragment
import com.c3.pcust30.data.info.UserInfo
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.ClientDataListRsp
import com.c3.pcust30.http.config.CLIENT_DATA_LIST_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.view_date_list_with_search.*

/**
 * 作者： LYJ
 * 功能： 客户管理界面
 * 创建日期： 2017/12/9
 */
class ClientManagementFragment : LoadRefreshListFragment() {
    private val clientList: MutableList<ClientDataListRsp.ClientInfo> by lazy { mutableListOf<ClientDataListRsp.ClientInfo>() }
    override fun setFragmentView(): Int = R.layout.fragment_client_management

    override fun setTitleText(): CharSequence = resources.getString(R.string.frag_title_client)

    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
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
    /**
     * 请求后台服务
     */
    override fun getDataFromServer(loadType: Int, loadPage: Int) {
        super.getDataFromServer(loadType, loadPage)
        val requestJson = getJson(TradingRequest()
                .addHeader(SERVICE_CODE, CLIENT_DATA_LIST_CODE)
                .addHeader(LOGIN_USER_NAME, UserInfo.userCode)
                .addHeader(TODAY_TASK_DATA_PAGE_NO, loadPage.toString())
                .addHeader(TODAY_TASK_DATA_PAGE_SIZE, pageDataCount)
                .addBody(LOGIN_USER_CODE, UserInfo.userCode)
                .addBody(LOGIN_ORG_CODE, UserInfo.orgCode)
                .addBody("followingstatus", filter1 ?: "")
                .addBody("accessway", filter2 ?: "")
                .addBody("cusinvestscale", filter3 ?: "")
                .addBody("inputValue", filter4 ?: "")
                .addBody("star", filter5 ?: "")
                .addBody("talkcount", filter6 ?: "")
                .addBody("talktime", filter7 ?: "")
                .addBody("latestdate", filter8 ?: "")
                .addBody("groupage", filter9 ?: ""))
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
            parseResult(dataCount, result, object : TypeToken<TradingResponse<ClientDataListRsp.ClientEntity>>() {},
                    object : TypeToken<TradingResponse<ClientDataListRsp.ClientArray>>() {},
                    { entityBody -> clientList.add(entityBody.PcustindiinfoView!!) },
                    { arrayBody -> clientList.addAll(arrayBody.PcustindiinfoView!!) })
            ShowHint.hint(mContext, "客户数量${clientList.size}")
        })
    }
}
