package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 作者： LYJ
 * 功能： 客户评级
 * 创建日期： 2017/12/8
 */

class ClientClassRateRsp : TradingResponseBody {
    var effView: List<RateInfo>? = null

    /**
     * 评级详情
     */
    class RateInfo {
        var customnum: String? = null// "2",
        var yorn: String? = null// "Y",
        var yorndesc: String? = null// "有效客户"
    }
}