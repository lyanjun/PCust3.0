package com.c3.pcust30.activity

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import com.c3.library.activity.NonSkidBaseActivity
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
        setContentView(R.layout.activity_main)
        val anim = load.drawable as AnimationDrawable
        anim.start()
    }
}
