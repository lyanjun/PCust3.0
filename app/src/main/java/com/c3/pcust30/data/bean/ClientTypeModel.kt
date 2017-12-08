package com.c3.pcust30.data.bean

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * 作者： LYJ
 * 功能： 客户分组
 * 创建日期： 2017/12/8
 */
data class ClientTypeModel(private val isHeader: Boolean, val header: String?) : SectionEntity<ClientTypeModel.ClientType>(isHeader, header) {
    data class ClientType(val title: String, val count: String, val unit: String, val flag: String, val iconRes: Int)
}