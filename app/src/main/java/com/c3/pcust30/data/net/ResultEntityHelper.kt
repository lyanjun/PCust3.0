package com.c3.pcust30.data.net

import android.text.TextUtils
import com.c3.pcust30.data.net.rsp.TradingResponse

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 获取返回结果
 * Created by Lyan on 17/12/2.
 */

class ResultEntityHelper {
    companion object {
        fun <T : TradingResponseBody> resultBody(response: TradingResponse<T>, Success: ((body: T) -> Unit)? = null, Failure: ((error: String) -> Unit)? = null) {
            if (TextUtils.equals(TRADING_SUCCESS, response.header!!.rspCode)) {
                Success?.invoke(response.body!!)
            } else {
                Failure?.invoke(response.header!!.rspMsg!!)
            }
        }
    }
}
