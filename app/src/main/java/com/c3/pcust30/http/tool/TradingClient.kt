package com.c3.pcust30.http.tool

import com.c3.pcust30.http.config.TRADING_LOCATION
import com.c3.pcust30.http.config.TradingService
import com.c3.pcust30.http.converter.TradingConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit



@Suppress("MemberVisibilityCanPrivate")//看见黄线有点难受
/**
 * 作者： LYJ
 * 功能： 请求对象
 * 创建日期： 2017/11/16
 */
object TradingClient {
    val retrofit: Retrofit
    val service: TradingService
    /**
     * 初始化
     */
    init {
        //网络请求基本配置(后期可以添加拦截器进行功能的拓展)
        val okHttpClient = OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
        //配置请求的地址，和返回数据的类型(转换后)
        retrofit = Retrofit.Builder()
                .baseUrl(TRADING_LOCATION)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(TradingConverterFactory.create())
                .build()
        service = retrofit.create(TradingService::class.java)
    }
}