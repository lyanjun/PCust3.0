package com.c3.pcust30.http.tool

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody


/**
 * 作者： LYJ
 * 功能： 网络请求工具类
 * 创建日期： 2017/11/16
 */

class TradingTool {

    companion object {
        private val JSON_TYPE = MediaType.parse("application/json; charset=utf-8")//提交类型
        /**
         * 发起对后台数据的请求(因为后台使用Json接收)
         */
        fun commitTrading(tradingJson: String): Observable<String> = TradingClient.service
                .getResultJson(RequestBody.create(JSON_TYPE, tradingJson)).compose(rxSchedulerHelper())

        /**
         * 不带线程切换的请求
         */
        fun commitTradingNewThread(tradingJson: String): Observable<String> = TradingClient.service
                .getResultJson(RequestBody.create(JSON_TYPE, tradingJson)).subscribeOn(Schedulers.io())
        /**
         * 统一线程处理
         */
        private fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}
