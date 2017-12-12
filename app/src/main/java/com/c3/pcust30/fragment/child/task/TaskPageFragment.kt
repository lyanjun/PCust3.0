package com.c3.pcust30.fragment.child.task

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.c3.library.view.title.IsTitleChildView
import com.c3.pcust30.R
import com.c3.pcust30.adapter.WaitTaskDataAdapter
import com.c3.pcust30.base.frag.LoadRefreshListFragment
import com.c3.pcust30.data.info.UserInfo
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.WaitDoTaskRsp
import com.c3.pcust30.http.config.TASK_AGENTS_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.c3.pcust30.top.bindDataWithSetShowType
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.fragment_task_page.*

/**
 * 作者： LYJ
 * 功能： 新增界面
 * 创建日期： 2017/11/27
 */

class TaskPageFragment : LoadRefreshListFragment(), OnRefreshListener, OnLoadmoreListener {
    private val taskDataList: MutableList<WaitDoTaskRsp.TaskInfo> by lazy { mutableListOf<WaitDoTaskRsp.TaskInfo>() }//数据
    private val taskAdapter: WaitTaskDataAdapter by lazy { WaitTaskDataAdapter(taskDataList) }//数据适配器
    /**
     * 设置布局
     */
    override fun setFragmentView(): Int = R.layout.fragment_task_page

    override fun setTitleLeftChildView(): IsTitleChildView? = null

    override fun setTitleText(): CharSequence = getString(R.string.frag_title_task)
    /**
     * 初始化设置
     */
    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        setSwipeBackEnable(false)//关闭滑动
        bindRefreshWithLoadMoreListener(refreshGroup)
        bindDataWithSetShowType(swipe_target, LinearLayoutManager(mContext), taskAdapter)//绑定数据
    }

    /**
     * 入场动画结束调用该方法
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        getDataFromServer()//请求待办任务
    }

    /**
     * 向后台发起请求，请求待办任务数据
     */
    override fun getDataFromServer(loadType: Int, loadPage: Int) {
        super.getDataFromServer(loadType, loadPage)
        showLoading()//显示等待弹窗
        TradingTool.commitTrading(getJson(TradingRequest().addHeader(SERVICE_CODE, TASK_AGENTS_CODE)
                .addHeader(LOGIN_USER_NAME, UserInfo.userCode).addBody(LOGIN_USER_CODE, UserInfo.userCode)
                .addBody(LOGIN_ORG_CODE, UserInfo.orgCode).addBody(TODAY_TASK_DATA_PAGE_NO, loadPage.toString())
                .addBody(TODAY_TASK_DATA_PAGE_SIZE, pageDataCount)))
                .doFinally {
                    if (refreshGroup.isRefreshing) refreshGroup.finishRefresh()
                    if (!refreshGroup.isLoadmoreFinished) refreshGroup.finishLoadmore()
                    hideLoading()
                }
                .bindToLifecycle(this)
                .subscribe({ taskJson -> getResponse(taskJson, loadType) },
                        { error -> Logger.t(TAG).w(error.message) })
    }

    /**
     * 处理请求结果
     */
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        val taskResponse = getResultBodyWithHeader(result, object : TypeToken<TradingResponse<WaitDoTaskRsp>>() {})//解析结果
        getResultBody(taskResponse, { bodyEntity ->
            //任务数量
            val taskCount = bodyEntity.taskCount?.taskCount
            //加载数据
            loadDataToListView(refreshGroup, taskCount, tag,
                    { taskDataList.clear() },
                    {
                        parseResult(taskCount!!, result, object : TypeToken<TradingResponse<WaitDoTaskRsp.WaitDoTaskEntity>>() {},
                                object : TypeToken<TradingResponse<WaitDoTaskRsp.WaitDoTaskArray>>() {},
                                { entityBody -> taskDataList.add(entityBody.taskInfo!!) },
                                { arrayBody -> taskDataList.addAll(arrayBody.taskInfo!!) })
                        //根据任务名称设置显示类型
                        taskDataList.forEach {
                            when (it.taskName) {
                                "PCustFollow" -> {//客户
                                    it.itemType = WaitTaskDataAdapter.C_TYPE
                                }
                                "PMerctFollow" -> {//商户
                                    it.itemType = WaitTaskDataAdapter.M_TYPE
                                }
                            }
                        }
                        taskAdapter.notifyDataSetChanged()
                    }, { --page })
        })
    }
}