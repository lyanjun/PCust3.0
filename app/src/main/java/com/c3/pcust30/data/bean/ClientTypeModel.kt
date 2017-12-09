package com.c3.pcust30.data.bean

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * 作者： LYJ
 * 功能： 客户分组
 * 创建日期： 2017/12/8
 */
class ClientTypeModel : SectionEntity<ClientTypeModel.ClientType> {
    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(clientType: ClientTypeModel.ClientType) : super(clientType)

    data class ClientType(val title: String, val count: String, val type: String, val flag: String, val iconRes: Int)
}