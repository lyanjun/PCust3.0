package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 作者： LYJ
 * 功能： 客户分类
 * 创建日期： 2017/12/8
 */

class ClientClassTypeRsp : TradingResponseBody {
    var countView: List<TypeInfo>? = null

    /**
     * 类型详情
     */
    class TypeInfo {
        var content: String? = null// "成功",
        var count: String? = null// "2",
        var status: String? = null// "01"
    }
}
