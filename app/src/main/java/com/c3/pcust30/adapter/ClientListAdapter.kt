package com.c3.pcust30.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.TextView
import com.c3.pcust30.R
import com.c3.pcust30.data.net.rsp.body.ClientDataListRsp
import com.c3.pcust30.data.net.rsp.body.MerchantDataListRsp
import com.c3.pcust30.tools.CircleColorUtils
import com.c3.pcust30.top.setItemStatusIcon
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * 作者： LYJ
 * 功能： 商户列表适配器
 * 创建日期： 2017/12/8
 */

class ClientListAdapter(data: List<ClientDataListRsp.ClientInfo>?) :
        BaseQuickAdapter<ClientDataListRsp.ClientInfo, BaseViewHolder>(R.layout.item_client_detail, data) {
    /**
     * 设置内容
     */
    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: ClientDataListRsp.ClientInfo) {
        val cusName = helper.getView<TextView>(R.id.cusName)
        CircleColorUtils.setCircleBackgroundColor(item.gid, cusName.background as GradientDrawable)
        cusName.text = "${item.custname?.substring(0 until 1)}${item.gender}"
        helper.setText(R.id.clientName, item.custname)
        helper.setText(R.id.clientTel, item.tel)
        helper.setText(R.id.clientFrom, item.accessway)
        helper.setText(R.id.clientState, item.status)
        setItemStatusIcon(helper.getView(R.id.taskStateIcon), item.followstatus ?: "")
    }
}
