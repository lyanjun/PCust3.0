package com.c3.pcust30.activity

import android.os.Bundle
import com.c3.library.activity.NonSkidBaseActivity
import com.c3.pcust30.R

/**
 * 作者： LYJ
 * 功能： 主界面
 * 创建日期： 2017/11/7
 */
class MainActivity : NonSkidBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_main)
//        val loadHelper = LoadDialogHelper()
//        loadHelper.setCustomLoadDialog(DefaultLoadingDialog(this))
//        test.setOnClickListener { loadHelper.showDialog() }
    }
}
