package com.c3.pcust30.activity

import android.os.Bundle
import android.view.View
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 作者： LYJ
 * 功能： 登录界面
 * 创建日期： 2017/11/7
 */
class LoginActivity : BaseActivity() , View.OnClickListener{

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.loginBtn -> ShowHint.warn(this,"提示")
            else -> ShowHint.failure(this,"错误")
        }
    }

    /**
     * 初始化
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_login)
        loginBtn.setOnClickListener(this)
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
