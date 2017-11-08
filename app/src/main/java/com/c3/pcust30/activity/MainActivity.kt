package com.c3.pcust30.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import com.c3.library.activity.NonSkidBaseActivity
import com.c3.library.helper.LoadDialogHelper
import com.c3.library.weight.load.impl.DefaultLoadingDialog
import com.c3.pcust30.R
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
    }
}
