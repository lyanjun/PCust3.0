package com.c3.pcust30.app

import com.c3.library.app.CustomApp
import com.c3.pcust30.app.helper.ActivityLifecycleCallbackImpl
import com.c3.pcust30.app.helper.InitHelper






/**
 * 作者： LYJ
 * 功能： 全局初始化
 * 创建日期： 2017/11/7
 */

class InitApplication : CustomApp(){
    /**
     * 初始化
     */
    override fun onCreate() {
        super.onCreate()
        //初始化设置
        InitHelper.initApplicationContextSetting(this)
        //Activity的声明周期回调
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbackImpl())
        //开启EventBus加速
//        EventBus.builder().addIndex(MyEventBusIndex()).installDefaultEventBus()
    }

}
