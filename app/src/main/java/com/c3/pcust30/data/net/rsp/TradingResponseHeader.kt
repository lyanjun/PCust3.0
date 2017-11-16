package com.c3.pcust30.data.net.rsp

/**
 * 返回头部(验证信息用)
 * Created by liyanjun on 2017/6/17.
 */

class TradingResponseHeader {
    var rspCode: String? = null//返回码
    var rspMsg: String? = null//返回信息
    var serviceCode: String? = null//服务号
    var pageNo: Int = 0//请求的页数
    var pageSize: Int = 0//每页数据的条数
    var counts: String? = null//返回数据的大小
}
