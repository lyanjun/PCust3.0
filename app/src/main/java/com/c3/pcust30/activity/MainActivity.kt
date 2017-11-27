package com.c3.pcust30.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.c3.library.view.title.CustomBodyView
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import com.c3.pcust30.data.event.MineEvents
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import me.majiajie.pagerbottomtabstrip.NavigationController
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit
import me.majiajie.pagerbottomtabstrip.item.NormalItemView
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem



/**
 * 作者： LYJ
 * 功能： 主界面
 * 创建日期： 2017/11/7
 */
class MainActivity : BaseActivity() {
    private var navigationController:NavigationController? = null//底部栏的控制器
    /**
     * 初始化界面
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_main)//设置根布局
        setSwipeBackEnable(false)//关闭滑动退出
        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).bindToLifecycle(this)
                .subscribe({ EventBus.getDefault().post(MineEvents.FinishActivityEvent()) })//发出一条让主界面之前的界面关闭的消息
        navigationController = bottomBar.custom()
                .addItem(newItem(R.drawable.pcust_bottom_tab_home_01,R.drawable.pcust_bottom_tab_home_02,getString(R.string.main_bottom_tab_home)))
                .addItem(newItem(R.drawable.pcust_bottom_tab_task_01,R.drawable.pcust_bottom_tab_task_02,getString(R.string.main_bottom_tab_task)))
                .addItem(newItem(R.drawable.pcust_bottom_tab_manage_01,R.drawable.pcust_bottom_tab_manage_02,getString(R.string.main_bottom_tab_manage)))
                .addItem(newItem(R.drawable.pcust_bottom_tab_add_01,R.drawable.pcust_bottom_tab_add_02,getString(R.string.main_bottom_tab_add)))
                .build()
    }

    /**
     * 主界面不实用标题栏（使用Fragment中的标题栏）
     */
    override fun setTitleBarShowType(): CustomBodyView.TitleShowType = CustomBodyView.TitleShowType.NONE

    /**
     * 设置选项显示的效果
     */
    private fun newItem(drawable: Int, checkedDrawable: Int, text: String): BaseTabItem {
        val normalItemView = NormalItemView(this)
        normalItemView.initialize(drawable, checkedDrawable, text)
        normalItemView.setTextDefaultColor(ContextCompat.getColor(this,R.color.bottom_tab_text_color_false))
        normalItemView.setTextCheckedColor(ContextCompat.getColor(this,R.color.bottom_tab_text_color_true))
        return normalItemView
    }
}
