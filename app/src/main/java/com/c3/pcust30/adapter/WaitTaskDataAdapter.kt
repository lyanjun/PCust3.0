package com.c3.pcust30.adapter

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.TextView
import com.c3.pcust30.R
import com.c3.pcust30.data.net.rsp.body.WaitDoTaskRsp
import com.c3.pcust30.tools.CircleColorUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * 代办任务列表数据适配器
 * Created by Lyan on 17/12/2.
 */

class WaitTaskDataAdapter(taskData: List<WaitDoTaskRsp.TaskInfo>) :
        BaseMultiItemQuickAdapter<WaitDoTaskRsp.TaskInfo, BaseViewHolder>(taskData) {
    /**
     * item类型
     */
    companion object {
        val C_TYPE = 1 //客户
        val M_TYPE = 2 //商户
    }

    /**
     * 设置布局
     */
    init {
        addItemType(C_TYPE, R.layout.item_wait_task_detail_c)
        addItemType(M_TYPE, R.layout.item_wait_task_detail_m)
    }

    /**
     * 展示数据
     */
    override fun convert(helper: BaseViewHolder, item: WaitDoTaskRsp.TaskInfo) {
        when (helper.itemViewType) {
            C_TYPE -> {//客户
                val cusName = helper.getView<TextView>(R.id.cusName)
                CircleColorUtils.setCircleBackgroundColor(item.custid, cusName.background as GradientDrawable)
                cusName.text = item.custname
                helper.setText(R.id.unknown, item.source)
            }
            M_TYPE -> {//商户
                CircleColorUtils.setCircleBackgroundColor(helper.getView<ImageView>(R.id.merIcon)
                        .background as GradientDrawable)
                helper.setText(R.id.merchants, item.gender)
            }
        }
        helper.setText(R.id.flow, item.processName)
        helper.setText(R.id.phone, item.tel)
        helper.setText(R.id.date, item.deadline)
        val stateIconRes = if (!item.due.isNullOrEmpty() && item.due.equals("1"))
            R.drawable.follow_task_state else 0
        helper.setImageResource(R.id.taskStateIcon, stateIconRes)
    }
}
