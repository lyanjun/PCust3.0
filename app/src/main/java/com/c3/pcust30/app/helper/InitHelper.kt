package com.c3.pcust30.app.helper

import android.content.Context
import com.c3.pcust30.BuildConfig
import com.c3.pcust30.R
import com.hss01248.dialog.StyledDialog
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * 作者： LYJ
 * 功能： 全局初始化帮助类
 * 创建日期： 2017/11/7
 */
class InitHelper {
    /**
     * 伴生对象
     */
    companion object {
        /**
         * 初始化
         */
        fun initApplicationContextSetting(applicationContext: Context) {
            initLogger(applicationContext)//初始化日志设置
            Hawk.init(applicationContext).build()//初始化轻量级储存框架（以键值对形式）
            StyledDialog.init(applicationContext)//设置弹窗工具类
        }

        /**
         * 初始化日志的设置
         */
        private fun initLogger(context: Context) {
            //设置日志输出的标记（使用项目名称），其他是使用默认设置
            val formatStrategy = PrettyFormatStrategy.newBuilder().tag(context.resources.getString(R.string.app_name)).build()
            //设置日志大印状态
            val logAdapter = object : AndroidLogAdapter(formatStrategy) {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return BuildConfig.DEBUG
                }
            }
            //为日志增加配置
            Logger.addLogAdapter(logAdapter)
        }
    }
}