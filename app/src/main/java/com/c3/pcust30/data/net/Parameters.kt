package com.c3.pcust30.data.net

import com.google.gson.Gson

/**
 * 作者： LYJ
 * 功能： 提交参数的键
 * 创建日期： 2017/11/16
 */
const val TRADING_SUCCESS = "000000" //交易执行成功
const val LOGIN_SUCCESS = "000000" //登录成功
//将对象生成json并返回字符串
fun getJson(any: Any): String = Gson().toJson(any)

//header
const val SERVICE_CODE = "serviceCode"//交易号

//提交参数(登录)
const val LOGIN_USER_CODE = "usercode"//登录账号
const val LOGIN_ORG_CODE = "orgcode"//组织编码
const val LOGIN_USER_NAME = "userCode"//组织编码
const val LOGIN_PASSWORD = "passwd"//登录密码


//修改密码(修改密码 -> 重置初始化密码)
const val RESET_OLD_PASSWORD = "oldPwd"//旧密码
const val RESET_NEW_PASSWORD = "newPwd"//新密码

//忘记密码(重置密码)
const val LOGIN_USER_PHOME = "phone"//登录用户的手机号