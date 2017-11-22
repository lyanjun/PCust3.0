package com.c3.pcust30.data.net.rsp

/**
 * 交易返回数据
 * Created by liyanjun on 2017/6/17.
 */

class TradingResponse<T : TradingResponseBody?> {
    var header: TradingResponseHeader? = null
    var body: T? = null
}
