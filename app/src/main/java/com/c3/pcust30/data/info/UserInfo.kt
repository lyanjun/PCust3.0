package com.c3.pcust30.data.info

import com.orhanobut.hawk.Hawk

/**
 * 作者： LYJ
 * 功能： 登录用户的信息
 * 创建日期： 2017/11/24
 */
object UserInfo {
    val userCode : String = Hawk.get<String>(USER_CODE)//用户编码
    val orgCode : String = Hawk.get<String>(ORG_CODE)//用户所在组织编码
}