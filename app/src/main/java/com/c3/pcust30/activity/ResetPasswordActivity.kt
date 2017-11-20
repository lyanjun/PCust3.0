package com.c3.pcust30.activity

import android.os.Bundle
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity

/**
 * 作者： LYJ
 * 功能： 重置密码界面
 * 创建日期： 2017/11/7
 */
class ResetPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_reset_password)
    }

    /**
     * 设置标题
     */
    override fun setTitleText(): CharSequence = getString(R.string.act_title_reset_password)
}
