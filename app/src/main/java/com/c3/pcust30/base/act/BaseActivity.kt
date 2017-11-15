package com.c3.pcust30.base.act

import android.annotation.SuppressLint
import com.c3.library.activity.MineActivity
import com.c3.library.constant.TitleChildTag
import com.c3.library.helper.LoadDialogHelper
import com.c3.library.view.title.*
import com.c3.library.weight.load.CustomLoadDialog
import com.c3.library.weight.load.impl.DefaultLoadingDialog
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.config.DEFAULT_TITLE_BACKGROUND_DRAWABLE
import com.c3.pcust30.base.config.DEFAULT_TITLE_TEXT_COLOR
import com.c3.pcust30.base.config.DEFAULT_TITLE_TEXT_TITLE_SIZE

/**
 * 作者： LYJ
 * 功能： Activity基类
 * 创建日期： 2017/11/10
 */
@SuppressLint("Registered")
open class BaseActivity : MineActivity() , CustomTitleChild.OnChildClickListener{
    //todo 在Fragment设置之后 需完成弹窗控制逻辑
    private val loadHelper = LoadDialogHelper()//用来管理加载等待弹窗
    override fun initSetting() {
        loadHelper.setCustomLoadDialog(setLoadingDialog())//设置加载等待弹窗
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
        if (TitleChildTag.BACK_BTN == childTag) ShowHint.hint(this,"点击了返回")
    }
    /**
     * 添加标题栏到左侧
     */
    open fun setTitleLeftChildView(): IsTitleChildView?
            = CustomTitleLeft(this).addOnChildClickListener(this)
            .setChildButtonImage(com.c3.library.R.drawable.default_back_icon,0)
    /**
     * 添加标题到栏中间
     */
    open fun setTitleCenterChildView(): IsTitleChildView? =
            CustomTitleCenter(this).setChildTextColor(DEFAULT_TITLE_TEXT_COLOR)
                    .setChildTextSize(DEFAULT_TITLE_TEXT_TITLE_SIZE)
                .setChildText(setTitleText(), 0).toChangeAllText()

    /**
     * 设置标题
     */
    open fun setTitleText(): CharSequence = getString(R.string.app_name)

    /**
     * 添加标题栏到右侧
     */
    open fun setTitleRightChildView(): IsTitleChildView? = null

    /**
     * 设置加载等待弹窗
     */
    open fun setLoadingDialog(): CustomLoadDialog = DefaultLoadingDialog(this)

}