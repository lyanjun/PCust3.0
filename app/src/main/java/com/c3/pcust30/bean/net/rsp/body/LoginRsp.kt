package com.c3.pcust30.bean.net.rsp.body

import com.c3.pcust30.bean.net.entity.WorkSignInfo
import com.c3.pcust30.bean.net.rsp.TradingResponseBody

/**
 * 作者： LYJ
 * 功能： 登录返回的数据
 * 创建日期： 2017/11/16
 */
class LoginRsp : TradingResponseBody{
    var worksigninfo : WorkSignInfo? = null//用户的信息
}