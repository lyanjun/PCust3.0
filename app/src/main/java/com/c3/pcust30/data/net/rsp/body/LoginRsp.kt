package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 作者： LYJ
 * 功能： 登录返回的数据
 * 创建日期： 2017/11/16
 */
class LoginRsp : TradingResponseBody {
    var worksigninfo: WorkSignInfo? = null//用户的信息

    class WorkSignInfo {
        var userid: String? = null//用户唯一编号
        var usercode: String? = null//用户编码
        var orgcode: String? = null//用户所在机构编码
        var logincode: String? = null//登录异常返回的编码
        var loginmsg: String? = null//登录异常返回描述
        var firstLogin: String? = null//首次登录标识, 0: 首次登录
        var userName: String? = null//用户姓名
        var idno: String? = null//用户身份证号码
        var orgName: String? = null//用户所在机构名称
        var phone: String? = null//用户注册的手机号码
    }
}