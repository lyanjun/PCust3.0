package com.c3.pcust30.fragment.child.task

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.ViewGroup
import com.c3.library.view.title.IsTitleChildView
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.adapter.WaitTaskDataAdapter
import com.c3.pcust30.base.frag.TopFragment
import com.c3.pcust30.data.info.UserInfo
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.WaitDoTaskRsp
import com.c3.pcust30.http.config.TASK_AGENTS_CODE
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
import kotlinx.android.synthetic.main.fragment_task_page.*

/**
 * 作者： LYJ
 * 功能： 新增界面
 * 创建日期： 2017/11/27
 */

class TaskPageFragment : TopFragment(), OnRefreshListener, OnLoadmoreListener {
    private val pageDataCount = "8"//每页请求的数据数量
    private var page = 1//请求的页数
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
        refreshGroup.setOnRefreshListener(this)
        refreshGroup.setOnLoadmoreListener(this)
        taskAdapter.setEmptyView(R.layout.view_data_empty, swipe_target.parent as ViewGroup)
        swipe_target.layoutManager = LinearLayoutManager(mContext)
        swipe_target.setHasFixedSize(true)
        swipe_target.adapter = taskAdapter//绑定适配器
    }

    /**
     * 入场动画结束调用该方法
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        getTaskDataListFromNet(LOAD_NONE, page)//请求待办任务
    }

    /**
     * 下拉刷新
     */
    override fun onRefresh(refreshlayout: RefreshLayout?) {
        getTaskDataListFromNet(LOAD_REFRESH, 1)
    }

    /**
     * 上拉加载
     */
    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        getTaskDataListFromNet(LOAD_MORE, ++page)
    }

    /**
     * 向后台发起请求，请求待办任务数据
     */
    private fun getTaskDataListFromNet(loadType: Int, pageNum: Int) {
        page = pageNum
        showLoading()//显示等待弹窗
        TradingTool.commitTrading(getJson(TradingRequest().addHeader(SERVICE_CODE, TASK_AGENTS_CODE)
                .addHeader(LOGIN_USER_NAME, UserInfo.userCode).addBody(LOGIN_USER_CODE, UserInfo.userCode)
                .addBody(LOGIN_ORG_CODE, UserInfo.orgCode).addBody(TODAY_TASK_DATA_PAGE_NO, pageNum.toString())
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
        val taskType = object : TypeToken<TradingResponse<WaitDoTaskRsp>>() {}.type//解析类型
        val taskResponse = Gson().fromJson<TradingResponse<WaitDoTaskRsp>>(result, taskType)//解析结果
        getResultBody(taskResponse, { bodyEntity ->
            //任务数量
            val taskCount = bodyEntity.taskCount?.taskCount
            //加载数据
            loadDataToListView(refreshGroup, taskCount, tag,
                    { taskDataList.clear() }, { showDataOnView(taskCount, result) }, { --page })
        })
    }

    /**
     * 将数据展示在视图中
     */
    private fun showDataOnView(taskCount: String?, result: String) {
        if (TextUtils.equals(taskCount, "1")) {
            //以对象形式解析数据
            val taskTypeEntity = object : TypeToken<TradingResponse<WaitDoTaskRsp.WaitDoTaskEntity>>() {}.type//解析类型
            val waitEntity = Gson().fromJson<TradingResponse<WaitDoTaskRsp.WaitDoTaskEntity>>(result, taskTypeEntity)//解析结果
            taskDataList.add(waitEntity.body!!.taskInfo!!)
        } else {
            //以数组形式解析数据
            val taskTypeArray = object : TypeToken<TradingResponse<WaitDoTaskRsp.WaitDoTaskArray>>() {}.type//解析类型
            val waitArray = Gson().fromJson<TradingResponse<WaitDoTaskRsp.WaitDoTaskArray>>(result, taskTypeArray)//解析结果
            taskDataList.addAll(waitArray.body!!.taskInfo!!)
        }
        //根据任务名称设置显示类型
        taskDataList.forEach {
            when (it.taskName) {
                "PCustFollow" -> {//客户
                    it.itemType = WaitTaskDataAdapter.C_TYPE
                }
                "PMerctFollow" -> {//商户
                    it.itemType = WaitTaskDataAdapter.M_TYPE
                }
                else -> {
                    ShowHint.failure(mContext, "当前数据有误，请联系后台")
                }
            }
        }
        taskAdapter.notifyDataSetChanged()
    }
}