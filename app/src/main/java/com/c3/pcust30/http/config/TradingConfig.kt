package com.c3.pcust30.http.config

/**
 * 交易配置
 * Created by Lyan on 17/11/15.
 */
//交易连接地址
// "http://119.23.18.110:8086/SisPCust/frontlineServlet"
//const val TRADING_LOCATION = "http://119.23.18.110:48009/"//地址(永吉)
const val TRADING_LOCATION = "http://119.23.18.110:8086/"//地址(长农)
//const val TRADING_LOCATION = "http://10.15.4.5:8099/"//地址(王坤)
const val TRADING_ADDRESS = "SisPCust/frontlineServlet"//连接

//交易号(相当于当个接口,一个交易号相当于一个交易接口请求)
const val LOGIN_TRADING_CODE = "002032"//登录交易号(LoginActivity)
const val RESET_PASSWORD_TRADING_CODE = "002033"//修改密码交易号(ResetPasswordActivity)
const val FORGET_PASSWORD_TRADING_CODE = "002035"//忘记密码交易号(ForgetPasswordActivity)

//进入到主界面之后的交易
const val CUSTOM_INFO_TRADING_CODE = "720025"//首页统计和排名列表(HomePageFragment)
const val CUSTOM_DATA_STATISTICS_TRADING_CODE = "720021"//客户经理月度客户数量趋势(HomePageFragment)
const val TASK_AGENTS_CODE = "720012"//待办任务列表(TaskPageFragment)
