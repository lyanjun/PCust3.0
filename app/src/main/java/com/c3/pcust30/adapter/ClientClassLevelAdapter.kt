package com.c3.pcust30.adapter

import com.c3.pcust30.R
import com.c3.pcust30.data.bean.ClientTypeModel
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * 作者： LYJ
 * 功能： 客户分级列表适配器
 * 创建日期： 2017/12/9
 */

class ClientClassLevelAdapter(data: List<ClientTypeModel>) : BaseSectionQuickAdapter<ClientTypeModel, BaseViewHolder>(
        R.layout.item_type_icon_with_title, R.layout.item_text_header, data) {
    /**
     * 分组
     */
    override fun convertHead(helper: BaseViewHolder, item: ClientTypeModel) {
        helper.setText(R.id.groupTitle, item.header)
    }

    /**
     * 组内成员
     */
    override fun convert(helper: BaseViewHolder, item: ClientTypeModel) {
        helper.addOnClickListener(R.id.itemChild)
        helper.setText(R.id.classHint, "${item.t.title}(${item.t.count}人)")
        helper.setImageResource(R.id.classIcon, item.t.iconRes)
    }
}
