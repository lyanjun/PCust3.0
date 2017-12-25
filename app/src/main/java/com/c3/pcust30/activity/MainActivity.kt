package com.c3.pcust30.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.SparseArray
import com.c3.library.fragment.GroupFragment
import com.c3.library.view.title.CustomBodyView
import com.c3.pcust30.R
import com.c3.pcust30.base.act.EventActivity
import com.c3.pcust30.data.event.MineEvents
import com.c3.pcust30.data.event.receiver.OnEventListener
import com.c3.pcust30.fragment.child.add.AddPageFragment
import com.c3.pcust30.fragment.child.home.HomePageFragment
import com.c3.pcust30.fragment.child.manage.ManagePageFragment
import com.c3.pcust30.fragment.child.task.TaskPageFragment
import com.c3.pcust30.fragment.group.Add
import com.c3.pcust30.fragment.group.Home
import com.c3.pcust30.fragment.group.Manage
import com.c3.pcust30.fragment.group.Task
import com.c3.pcust30.tools.map.LocationTool
import com.c3.pcust30.top.FRAG_ADD
import com.c3.pcust30.top.FRAG_HOME
import com.c3.pcust30.top.FRAG_MANAGE
import com.c3.pcust30.top.FRAG_TASK
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem
import me.majiajie.pagerbottomtabstrip.item.NormalItemView
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit


/**
 * 作者： LYJ
 * 功能： 主界面
 * 创建日期： 2017/11/7
 */
class MainActivity : EventActivity(), OnTabItemSelectedListener, OnEventListener {
    private val mFragments = SparseArray<GroupFragment>(4)//模块布局
    private var navigationController: NavigationController? = null//底部栏的控制器
    /**
     * 初始化界面
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_main)//设置根布局
        setSwipeBackEnable(false)//关闭滑动退出
        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).bindToLifecycle(this)
                .subscribe({ EventBus.getDefault().post(MineEvents.FinishActivityEvent()) })//发出一条让主界面之前的界面关闭的消息
        navigationController = bottomBar.custom()//添加底部选项
                .addItem(newItem(R.drawable.pcust_bottom_tab_home_01, R.drawable.pcust_bottom_tab_home_02, getString(R.string.main_bottom_tab_home)))
                .addItem(newItem(R.drawable.pcust_bottom_tab_task_01, R.drawable.pcust_bottom_tab_task_02, getString(R.string.main_bottom_tab_task)))
                .addItem(newItem(R.drawable.pcust_bottom_tab_manage_01, R.drawable.pcust_bottom_tab_manage_02, getString(R.string.main_bottom_tab_manage)))
                .addItem(newItem(R.drawable.pcust_bottom_tab_add_01, R.drawable.pcust_bottom_tab_add_02, getString(R.string.main_bottom_tab_add)))
                .build()
        navigationController!!.addTabItemSelectedListener(this)
        //添加模块
        initFragment()//初始化模块视图（设置每个模块的一级视图）
        //获取位置信息
        LocationTool.toLocation(LocationTool.LocationType.START)
    }

    /**
     * 初始化模块
     */
    private fun initFragment() {
        val firstFragment = findFragment(Home::class.java)
        if (firstFragment == null) {//判断栈中是否有该对象
            mFragments.append(FRAG_HOME, Home().setFirstChildView(HomePageFragment(), HomePageFragment::class.java))
            mFragments.append(FRAG_TASK, Task().setFirstChildView(TaskPageFragment(), TaskPageFragment::class.java))
            mFragments.append(FRAG_MANAGE, Manage().setFirstChildView(ManagePageFragment(), ManagePageFragment::class.java))
            mFragments.append(FRAG_ADD, Add().setFirstChildView(AddPageFragment(), AddPageFragment::class.java))
            //添加要加载的视图
            loadMultipleRootFragment(R.id.mainHome, FRAG_HOME, mFragments.get(FRAG_HOME), mFragments.get(FRAG_TASK),
                    mFragments.get(FRAG_MANAGE), mFragments.get(FRAG_ADD))
        } else {//没有就取出栈中的对象
            mFragments.put(FRAG_HOME, firstFragment)
            mFragments.put(FRAG_TASK, findFragment(Task::class.java))
            mFragments.put(FRAG_MANAGE, findFragment(Manage::class.java))
            mFragments.put(FRAG_ADD, findFragment(Add::class.java))
        }
    }

    /**
     * 主界面不实用标题栏（使用Fragment中的标题栏）
     */
    override fun setTitleBarShowType(): CustomBodyView.TitleShowType = CustomBodyView.TitleShowType.NONE

    /**
     * 设置选项显示的效果
     * (选项tab后期可以使用自定义拓展)
     */
    private fun newItem(drawable: Int, checkedDrawable: Int, text: String): BaseTabItem {
        val normalItemView = NormalItemView(this)
        normalItemView.initialize(drawable, checkedDrawable, text)
        normalItemView.setTextDefaultColor(ContextCompat.getColor(this, R.color.bottom_tab_text_color_false))
        normalItemView.setTextCheckedColor(ContextCompat.getColor(this, R.color.bottom_tab_text_color_true))
        return normalItemView
    }

    /**
     * 设置加载弹窗显示的效果
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onEventListener(event: MineEvents) {
        when (event) {
            is MineEvents.MainActivityLoadingState -> {//显示或隐藏弹窗
                if (event.showLoading) loadHelper.showDialogWithDismissFirst() else loadHelper.hideDialog()
            }
            is MineEvents.MainChangeModule -> {//切换到待办任务界面
                if (event.changeToTask) navigationController?.setSelect(1)
            }
        }
    }

    /**
     * 选项(切换界面)
     */
    override fun onSelected(index: Int, old: Int) {
        showHideFragment(mFragments.get(index))//显示模块
    }

    /**
     * 重复点击(回到没一个模块的起始页)
     */
    override fun onRepeat(index: Int) {
        when(index){
            FRAG_HOME -> (mFragments.get(index)).popToChild(HomePageFragment::class.java,false)
            FRAG_TASK -> (mFragments.get(index)).popToChild(TaskPageFragment::class.java,false)
            FRAG_MANAGE -> (mFragments.get(index)).popToChild(ManagePageFragment::class.java,false)
            FRAG_ADD -> (mFragments.get(index)).popToChild(AddPageFragment::class.java,false)
        }
    }

    /**
     * 界面销毁后定制定位
     */
    override fun onDestroy() {
        LocationTool.toLocation(LocationTool.LocationType.STOP)
        super.onDestroy()
    }
}
