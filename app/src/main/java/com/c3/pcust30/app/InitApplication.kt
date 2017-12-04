package com.c3.pcust30.app

import com.c3.library.app.CustomApp
import com.c3.library.utils.DynamicTimeFormat
import com.c3.pcust30.MyEventBusIndex
import com.c3.pcust30.app.helper.ActivityLifecycleCallbackImpl
import com.c3.pcust30.app.helper.InitHelper
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import org.greenrobot.eventbus.EventBus


/**
 * 作者： LYJ
 * 功能： 全局初始化
 * 创建日期： 2017/11/7
 */

class InitApplication : CustomApp() {
    companion object {
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreater { context, _ ->
                ClassicsHeader(context).setTimeFormat(DynamicTimeFormat("更新于 %s"))
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreater { context, _ ->
                ClassicsFooter(context)
            }
        }
    }
    /**
     * 初始化
     */
    override fun onCreate() {
        super.onCreate()
        //初始化设置
        InitHelper.initApplicationContextSetting(this)
        //Activity的声明周期回调
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbackImpl())
        //开启EventBus加速(使用Subscribe注解即可解开封印)
        EventBus.builder().addIndex(MyEventBusIndex()).installDefaultEventBus()
    }
}
