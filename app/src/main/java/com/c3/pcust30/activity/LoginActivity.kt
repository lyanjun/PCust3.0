package com.c3.pcust30.activity

import android.os.Bundle
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
/**
 * 作者： LYJ
 * 功能： 登录界面
 * 创建日期： 2017/11/7
 */
class LoginActivity : BaseActivity() {

    /**
     * 初始化
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_login)
    }

    /**
     * 设置标题
     */
    override fun setTitleText() = getString(R.string.act_title_login)!!

    /**
     * 设置没有返回键
     */
    override fun setTitleLeftChildView() = null
}
