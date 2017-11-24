package com.c3.pcust30.base.act

import android.annotation.SuppressLint
import android.os.Bundle
import org.greenrobot.eventbus.EventBus

@SuppressLint("Registered")
/**
 * 作者： LYJ
 * 功能： 接收消息的界面
 * 创建日期： 2017/11/24
 */
open class EventActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)//注册消息接收
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)//反注册
        super.onDestroy()
    }
}