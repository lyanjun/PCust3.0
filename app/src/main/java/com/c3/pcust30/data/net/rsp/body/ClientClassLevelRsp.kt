package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 作者： LYJ
 * 功能： 客户评级
 * 创建日期： 2017/12/8
 */

class ClientClassLevelRsp : TradingResponseBody{
    var viewsCount: ViewsCount? = null

    /**
     * 标签详情
     */
    class LevelInfo {
        var usercode: String? = null// "",
        var customnum: String? = null// "4",
        var labelname: String? = null// "订票",
        var gid: String? = null// "1"
    }

    /**
     * 标签数量
     */
    class ViewsCount {
        var viewsCount: String? = null
    }

    /**
     * 标签对象
     */
    class LevelInfoEntity : TradingResponseBody{
        var pculabel: LevelInfo? = null
    }

    /**
     * 标签数组
     */
    class LevelInfoArray : TradingResponseBody {
        var pculabel: List<LevelInfo>? = null
    }
}