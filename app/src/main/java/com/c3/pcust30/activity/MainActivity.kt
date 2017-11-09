package com.c3.pcust30.activity

import android.graphics.Color
import android.os.Bundle
import com.c3.library.activity.NonSkidBaseActivity
import com.c3.library.helper.LoadDialogHelper
import com.c3.library.view.*
import com.c3.library.weight.load.impl.DefaultLoadingDialog
import com.c3.pcust30.R
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 作者： LYJ
 * 功能： 主界面
 * 创建日期： 2017/11/7
 */
class MainActivity : NonSkidBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_main)
        val loadHelper = LoadDialogHelper()
        loadHelper.setCustomLoadDialog(DefaultLoadingDialog(this))
        test.setOnClickListener { loadHelper.showDialog() }
        //左侧
        mTitleView.addChildView(CustomTitleLeft(this)
                .setChildButtonImage(com.c3.library.R.drawable.default_back_icon , 0)
                ,IsTitleView.LEFT)
        //中间
        mTitleView.addChildView(CustomTitleCenter(this).setChildTextColor(Color.WHITE).setChildTextSize(18)
                .setChildText("标题" , 0).toChangeAllText() , IsTitleView.CENTER)
        //右侧
        mTitleView.addChildView(rightView(),IsTitleView.RIGHT)
        //设置背景色
//        mTitleView.self.setBackgroundColor(Color.RED)
        Logger.i("46dp = ${resources.getDimensionPixelSize(R.dimen.dp_46)}像素")
    }

    private fun rightView(): IsTitleChildView? {

        return CustomTitleRight(this).setChildTextColorList(com.c3.library.R.color.defualt_text_btn_color_list)
                .setChildText("测试" , 0).toChangeAllText()
    }
}
