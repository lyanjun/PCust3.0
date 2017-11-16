package com.c3.pcust30.bean.net

import com.google.gson.Gson

/**
 * 作者： LYJ
 * 功能： 提交参数的键
 * 创建日期： 2017/11/16
 */
const val TRADING_SUCCESS = "000000" //交易执行成功

//将对象生成json并返回字符串
fun getJson(any: Any): String = Gson().toJson(any)

//header
const val SERVICE_CODE = "serviceCode"//交易号

//登录提交参数
const val LOGIN_USER_CODE = "usercode"//登录账号
const val LOGIN_PASSWORD = "passwd"//登录密码
