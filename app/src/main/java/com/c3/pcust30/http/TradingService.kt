package com.c3.pcust30.http

import io.reactivex.Observable
import retrofit2.http.POST
import okhttp3.RequestBody
import retrofit2.http.Body


/**
 * 交易请求对象(用来请求后台数据)
 * Created by Lyan on 17/11/15.
 */
interface TradingService {
    /**
     * 向后台发送JSON，后台返回JSON
     */
    @POST(TRADING_ADDRESS)
    fun getResultJson(@Body requestBody: RequestBody): Observable<String>
}