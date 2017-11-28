package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 作者： LYJ
 * 功能： 用户折线统计图表的数据
 * 创建日期： 2017/11/28
 */
open class UserWorkChartRsp : TradingResponseBody {
    var stacount: DataCount? = null//数据个数

    class DataCount {
        var stacount: Int? = 0//数据的个数
    }

    class DataEntity {
        var custcount: Int = 0//数量
        var sdate: String? = null//统计日期
    }

    //返回只有一个对象
    class ChartEntityRsp : UserWorkChartRsp() {
        var pCustAcctCustCount: DataEntity? = null
    }

    //返回为数组
    class ChartArrayRsp : UserWorkChartRsp() {
        var pCustAcctCustCount: List<DataEntity>? = null
    }
}
