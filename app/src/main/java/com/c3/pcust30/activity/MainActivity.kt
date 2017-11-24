package com.c3.pcust30.activity

import android.os.Bundle
import com.c3.library.view.title.CustomBodyView
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import com.c3.pcust30.data.event.MineEvents
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * 作者： LYJ
 * 功能： 主界面
 * 创建日期： 2017/11/7
 */
class MainActivity : BaseActivity() {
    /**
     * 初始化界面
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_main)//设置根布局
        setSwipeBackEnable(false)//关闭滑动退出
        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).bindToLifecycle(this)
                .subscribe({ EventBus.getDefault().post(MineEvents.FinishActivityEvent()) })//发出一条让主界面之前的界面关闭的消息
    }

    /**
     * 主界面不实用标题栏（使用Fragment中的标题栏）
     */
    override fun setTitleBarShowType(): CustomBodyView.TitleShowType = CustomBodyView.TitleShowType.NONE
}
