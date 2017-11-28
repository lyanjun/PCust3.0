package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 用户工作统计信息详情
 * Created by Lyan on 17/11/27.
 */
class UserWorkInfoRsp : TradingResponseBody {

    var dataInfo: DataInfo? = null//用户任务信息
    var rangeRecView: MutableList<RangeRecView>? = null//用户排行列表

    /**
     * 用户工作详情
     */
    class DataInfo {
        var taskCount: Int = 0//待办任务数量
        var stadate: String? = null//统计截至时间
        var monRange: Int = 0//月度排名
        var yearRange: Int = 0//年度排名
        var differencial: Int = 0//与上一排名的差值
        var totalAcctCount: Int = 0//参与排名的客户经理数量-->
        var custCount: Int = 0//本月营销客户数量
        var investCount: Int = 0//本月投资客户数量
    }

    /**
     * 排名基本信息
     */
    class RangeRecView {
        var usercode: String? = null//用户编码
        var username: String? = null//用户姓名
        var range: Int = 0//排名
        var custcount: Int = 0//用户数量
        var isSelf: String? = null//是否自己 1:是, 其他:否
    }
}