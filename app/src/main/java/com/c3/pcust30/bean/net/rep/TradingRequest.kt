package com.c3.pcust30.bean.net.rep

@Suppress("MemberVisibilityCanPrivate")
/**
 * 作者： LYJ
 * 功能： 请求包装对象
 * 创建日期： 2017/11/16
 */
class TradingRequest {

    private val header = HashMap<String,Any>()//平台用
    private val body = HashMap<String,Any>()//请求后台用

    fun addHeader(parameter:String ,value :Any) : TradingRequest{
        header.put(parameter,value)
        return this
    }

    fun addBody(parameter:String ,value :Any) : TradingRequest{
        body.put(parameter,value)
        return this
    }
}