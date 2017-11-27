package com.c3.pcust30.base.frag

import android.os.Bundle
import com.c3.library.constant.TitleChildTag
import com.c3.library.fragment.ChildFragment
import com.c3.library.view.title.*
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.config.DEFAULT_TITLE_BACKGROUND_DRAWABLE
import com.c3.pcust30.base.config.DEFAULT_TITLE_TEXT_COLOR
import com.c3.pcust30.base.config.DEFAULT_TITLE_TEXT_TITLE_SIZE
import com.c3.pcust30.data.event.MineEvents
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus

/**
 * 作者： LYJ
 * 功能： Fragment抽象基类
 * 创建日期： 2017/11/15
 */
abstract class BaseFragment : ChildFragment(), CustomTitleChild.OnChildClickListener{
    @Suppress("LeakingThis", "PropertyName")
    protected val TAG = this::class.java.simpleName!!//TAG
    /**
     * 初始化成员变量（设置控件和设置成员）
     */
    //todo 加载等待弹窗关联（构思中）
    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        mTitleView.addChildView(setTitleLeftChildView() , IsTitleView.LEFT)//添加标题栏到左侧
        mTitleView.addChildView(setTitleCenterChildView() , IsTitleView.CENTER)//添加标题到栏中间
        mTitleView.addChildView(setTitleRightChildView() , IsTitleView.RIGHT)//添加标题栏到右侧
        setTitleBackGround()//设置标题栏的背景
    }
    /**
     * 设为可重写，可以使子类避免
     */
    open fun setTitleBackGround(){
        mTitleView.self.setBackgroundResource(DEFAULT_TITLE_BACKGROUND_DRAWABLE)
    }

    /**
     * 点击事件
     */
    override fun onChildClick(parentTag: Int, childTag: Int) {
        if (TitleChildTag.BACK_BTN == childTag) ShowHint.hint(mContext,"点击了返回")
    }
    /**
     * 添加标题栏到左侧
     */
    open fun setTitleLeftChildView(): IsTitleChildView?
            = CustomTitleLeft(mContext).addOnChildClickListener(this)
            .setChildButtonImage(com.c3.library.R.drawable.default_back_icon,0)
    /**
     * 添加标题到栏中间
     */
    open fun setTitleCenterChildView(): IsTitleChildView? =
            CustomTitleCenter(mContext).setChildTextColor(DEFAULT_TITLE_TEXT_COLOR)
                    .setChildTextSize(DEFAULT_TITLE_TEXT_TITLE_SIZE)
                    .setChildText(setTitleText(), 0).toChangeAllText()
    /**
     * 对请求返回的内容进行处理(解析和展示)
     */
    open protected fun getResponse(result: String, tag: Int = 0) {
        Logger.t(TAG).i("返回Json$result")
        Logger.t(TAG).json(result)//以JSON格式打印数据
    }
    /**
     * 设置标题
     */
    open fun setTitleText(): CharSequence = resources.getString(R.string.app_name)

    /**
     * 添加标题栏到右侧
     */
    open fun setTitleRightChildView(): IsTitleChildView? = null

    /**
     * 通知挂在的activity开启等待弹窗
     */
    protected fun showLoading(){
        EventBus.getDefault().post(MineEvents.MainActivityLoadingState(true))
    }
    /**
     * 通知挂在的activity开启关闭弹窗
     */
    protected fun hideLoading(){
        EventBus.getDefault().post(MineEvents.MainActivityLoadingState(false))
    }
}