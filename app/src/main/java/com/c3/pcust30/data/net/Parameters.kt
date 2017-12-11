package com.c3.pcust30.data.net

import android.text.TextUtils
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.TradingResponseBody
import com.c3.pcust30.top.LOAD_MORE
import com.c3.pcust30.top.LOAD_REFRESH
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
 * 作者： LYJ
 * 功能： 提交参数的键
 * 创建日期： 2017/11/16
 */
const val TRADING_SUCCESS = "000000" //交易执行成功
const val LOGIN_SUCCESS = "000000" //登录成功
/**
 * 将对象生成json并返回字符串
 */
fun getJson(any: Any): String = Gson().toJson(any)

/**
 * 判断是否有数据
 */
fun dataIsNotNull(values: String?): Boolean = !values.isNullOrEmpty() && !values.equals("0") && !values.equals("-1")

/**
 * 判断列表加载数据
 */
fun loadDataToListView(refreshLayout: RefreshLayout, dataCounts: String?, tag: Int, Refresh: (() -> Unit)?,
                       LoadData: (() -> Unit)?, NoLoadMore: (() -> Unit)?, Error: (() -> Unit)? = null) {
    if (dataIsNotNull(dataCounts)) {
        if (tag == LOAD_REFRESH) {//刷新
            Refresh?.invoke()
            refreshLayout.resetNoMoreData()
        }
        LoadData?.invoke()
    } else {
        if (tag == LOAD_MORE) {
            NoLoadMore?.invoke()
            refreshLayout.finishLoadmoreWithNoMoreData()
        }
        Error?.invoke()
    }
}

/**
 * 判断是否有数据
 */
@Suppress("SENSELESS_COMPARISON")
fun <T> listIsNotNull(list: List<T>, IsNotNull: ((size: Int) -> Unit)) {
    if (null != list && list.isNotEmpty()) {
        IsNotNull.invoke(list.size)
    }
}

/**
 * 根据数量判断解析模式
 */
fun <E : TradingResponseBody, A : TradingResponseBody>
        parseResult(dataCount: String, resultJson: String, entityType: TypeToken<TradingResponse<E>>,
                    arrayType: TypeToken<TradingResponse<A>>, EntityBody: ((entityBody: E) -> Unit), ArrayBody: ((arrayBody: A) -> Unit)) {
    if (TextUtils.equals(dataCount, "1")) {//按对象解析
        EntityBody.invoke(getResultOnlyBody(resultJson, entityType))
    } else {//按数组解析
        ArrayBody.invoke(getResultOnlyBody(resultJson, arrayType))
    }
}

/**
 *  解析指定类型的数据
 */
fun <R : TradingResponseBody> getResultOnlyBody(resultJson: String, typeToken: TypeToken<TradingResponse<R>>): R =
        Gson().fromJson<TradingResponse<R>>(resultJson, typeToken.type).body!!//解析结果

/**
 *  解析指定类型的数据(包含头部)
 */
fun <R : TradingResponseBody> getResultBodyWithHeader(resultJson: String, typeToken: TypeToken<TradingResponse<R>>): TradingResponse<R> =
        Gson().fromJson<TradingResponse<R>>(resultJson, typeToken.type)//解析结果

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
const val LOGIN_USER_PHONE = "phone"//登录用户的手机号

//代办任务列表
const val TODAY_TASK_DATA_PAGE_NO = "pageNo"//页数
const val TODAY_TASK_DATA_PAGE_SIZE = "pageSize"//条目
const val DEFAULT_PAGE_SIZE = "8"//默认每页数据的容量
const val DEFAULT_PAGE_NUMBER = 1//默认请求的页数


