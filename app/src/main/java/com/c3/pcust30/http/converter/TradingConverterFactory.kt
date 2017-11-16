package com.c3.pcust30.http.converter

import android.support.annotation.NonNull
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type


/**
 * 作者： LYJ
 * 功能： 交易结果转换器(只对改应用有效String类型)
 * 创建日期： 2017/11/16
 */
class TradingConverterFactory private constructor() : Converter.Factory() {
    /**
     * 获取转换工厂对象
     */
    companion object {
        fun create(): TradingConverterFactory {
            return TradingConverterFactory()
        }
    }
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return if (type === String::class.java) {
            StringResponseBodyConverter.INSTANCE
        } else null
    }

    private class StringResponseBodyConverter : Converter<ResponseBody, String> {
        @Throws(IOException::class)
        override fun convert(@NonNull value: ResponseBody): String {
            val jsonStr = value.string()
            return jsonStr.substring(1, jsonStr.length - 1).trim()
        }
        companion object {
            internal val INSTANCE = StringResponseBodyConverter()
        }
    }
}