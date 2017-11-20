package com.c3.pcust30.app.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.hss01248.dialog.ActivityStackManager

/**
 * 作者： LYJ
 * 功能： Activity声明中期的回调(用于弹窗框架的绑定事件)
 * 创建日期： 2017/11/20
 */
class ActivityLifecycleCallbackImpl : Application.ActivityLifecycleCallbacks{
    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity?) {}

    override fun onActivityStarted(activity: Activity?) {}

    override fun onActivityDestroyed(activity: Activity?) {
        ActivityStackManager.getInstance().removeActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityStopped(activity: Activity?){}

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        ActivityStackManager.getInstance().addActivity(activity)
    }
}