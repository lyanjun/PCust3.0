package com.c3.pcust30.http.tool

import com.c3.pcust30.data.info.UserInfo
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest

/**
 * 作者： LYJ
 * 功能： 请求Json工具类
 * 创建日期： 2017/12/8
 */

class RequestJsonUtils{
    companion object {
        /**
         * 请求json数据(基本请求)
         */
        fun requestJsonWithUserOrg(tradingCode: String) = getJson(TradingRequest()
                .addHeader(SERVICE_CODE, tradingCode).addHeader(LOGIN_USER_NAME, UserInfo.userCode)
                .addBody(LOGIN_USER_CODE, UserInfo.userCode).addBody(LOGIN_ORG_CODE, UserInfo.orgCode))
        /**
         * 请求json数据(带有标识的请求)
         */
        fun requestJsonWithUserOrg(tradingCode: String , flag : String) = getJson(TradingRequest()
                .addHeader(SERVICE_CODE, tradingCode).addHeader(LOGIN_USER_NAME, UserInfo.userCode)
                .addBody(LOGIN_USER_CODE, UserInfo.userCode).addBody(LOGIN_ORG_CODE, UserInfo.orgCode)
                .addBody("flag",flag))
    }
}
