package com.c3.pcust30.activity

import android.os.Bundle
import com.c3.library.view.title.CustomBodyView
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import com.c3.pcust30.fragment.TestFragment

/**
 * 作者： LYJ
 * 功能： 主界面
 * 创建日期： 2017/11/7
 */
class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_main)//设置根布局
        setSwipeBackEnable(false)//关闭滑动退出
        supportFragmentManager.beginTransaction().add(R.id.home, TestFragment()).commit()
    }

    override fun setTitleBarShowType(): CustomBodyView.TitleShowType = CustomBodyView.TitleShowType.NONE
}
