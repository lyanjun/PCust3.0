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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.fragment_task_page.*

/**
 * 作者： LYJ
 * 功能： 新增界面
 * 创建日期： 2017/11/27
 */

class TaskPageFragment : TopFragment() {
    private val pageDataCount = "8"//每页请求的数据数量
    private var page: Int = 0//请求的页数
    private val taskDataList: MutableList<WaitDoTaskRsp.TaskInfo> = mutableListOf()//数据
    private val taskAdapter: WaitTaskDataAdapter = WaitTaskDataAdapter(taskDataList)//数据适配器
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
        refreshGroup.finishRefresh(2000)
        refreshGroup.finishLoadmore(2000)
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
        //请求待办任务
        getTaskDataListFromNet()
    }

    /**
     * 向后台发起请求，请求待办任务数据
     */
    private fun getTaskDataListFromNet() {
        showLoading()//显示等待弹窗
        TradingTool.commitTrading(getJson(TradingRequest().addHeader(SERVICE_CODE, TASK_AGENTS_CODE)
                .addHeader(LOGIN_USER_NAME, UserInfo.userCode).addBody(LOGIN_USER_CODE, UserInfo.userCode)
                .addBody(LOGIN_ORG_CODE, UserInfo.orgCode).addBody(TODAY_TASK_DATA_PAGE_NO, page.toString())
                .addBody(TODAY_TASK_DATA_PAGE_SIZE, pageDataCount)))
                .doFinally { hideLoading() }
                .bindToLifecycle(this)
                .subscribe({ taskJson -> getResponse(taskJson) },
                        { error -> Logger.t(TAG).w(error.message) })
    }


    /**
     * 处理请求结果
     */
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        var taskType = object : TypeToken<TradingResponse<WaitDoTaskRsp>>() {}.type//解析类型
        val taskResponse = Gson().fromJson<TradingResponse<WaitDoTaskRsp>>(result, taskType)//解析结果
        getResultBody(taskResponse, { bodyEntity ->
            //任务数量
            val taskCount = bodyEntity.taskCount?.taskCount
            //在有任务的情况下
            if (dataIsNotNull(taskCount)) {
                if (TextUtils.equals(taskCount, "1")) {
                    //以对象形式解析数据
                    taskType = object : TypeToken<TradingResponse<WaitDoTaskRsp.WaitDoTaskEntity>>() {}.type//解析类型
                    val waitEntity = Gson().fromJson<TradingResponse<WaitDoTaskRsp.WaitDoTaskEntity>>(result, taskType)//解析结果
                    taskDataList.add(waitEntity.body!!.taskInfo!!)
                } else {
                    //以数组形式解析数据
                    taskType = object : TypeToken<TradingResponse<WaitDoTaskRsp.WaitDoTaskArray>>() {}.type//解析类型
                    val waitArray = Gson().fromJson<TradingResponse<WaitDoTaskRsp.WaitDoTaskArray>>(result, taskType)//解析结果
                    taskDataList.addAll(waitArray.body!!.taskInfo!!)
                }
            }
//            (0 until taskDataList.size step 2).map { taskDataList[it].taskName = "PMerctFollow" }
//            (0 until taskDataList.size step 2).map { taskDataList[it].due = "" }
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
                        ShowHint.failure(mContext,"当前数据有误，请联系后台")
                    }
                }
            }
            taskAdapter.notifyDataSetChanged()
            ShowHint.hint(mContext, "解析后的数据数量 : ${taskDataList.size}")
        })
    }
}