package com.c3.pcust30.adapter

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import com.c3.pcust30.R
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

class MerchantListAdapter(data: List<MerchantDataListRsp.MerchantInfo>?) :
        BaseQuickAdapter<MerchantDataListRsp.MerchantInfo, BaseViewHolder>(R.layout.item_merchant_detail, data) {
    /**
     * 设置内容
     */
    override fun convert(helper: BaseViewHolder, item: MerchantDataListRsp.MerchantInfo) {
        CircleColorUtils.setCircleBackgroundColor(helper.getView<ImageView>(R.id.merIcon)
                .background as GradientDrawable)
        helper.setText(R.id.merchantName, item.name)
        helper.setText(R.id.merchantTel, item.tel)
        helper.setText(R.id.merchantPer, item.reception)
        helper.setText(R.id.merchantState, item.status)
        setItemStatusIcon(helper.getView(R.id.taskStateIcon), item.followstatus ?: "")
    }
}
