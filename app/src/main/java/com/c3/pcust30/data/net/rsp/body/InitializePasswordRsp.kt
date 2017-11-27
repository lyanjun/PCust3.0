package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 作者： LYJ
 * 功能： 初始化密码的返回结果
 * 创建日期： 2017/11/23
 */
class InitializePasswordRsp : TradingResponseBody {
    var defaultpwd: DefaultPwd? = null

    /**
     * 默认的初始化密码
     */
    class DefaultPwd {
        var defaultpwd: String? = null
    }
}