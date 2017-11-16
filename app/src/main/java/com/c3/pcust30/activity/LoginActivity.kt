package com.c3.pcust30.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.c3.library.utils.MD5Utils
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import com.c3.pcust30.bean.net.LOGIN_PASSWORD
import com.c3.pcust30.bean.net.LOGIN_USER_CODE
import com.c3.pcust30.bean.net.SERVICE_CODE
import com.c3.pcust30.bean.net.getJson
import com.c3.pcust30.bean.net.rep.TradingRequest
import com.c3.pcust30.http.config.LOGIN_TRADING_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 作者： LYJ
 * 功能： 登录界面
 * 创建日期： 2017/11/7
 */
class LoginActivity : BaseActivity(), View.OnClickListener {

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.loginBtn -> login()//登录
            else -> ShowHint.failure(this, "错误")
        }
    }

    /**
     * 登录请求
     */
    private fun login() {
        val userCodeStr = inputUserCode.text.toString().trim()//用户账号
        var passwordStr = inputPassWord.text.toString().trim()//用户密码
        if (loginHintMessage(userCodeStr, getString(R.string.input_warn_user_code))) return
        if (loginHintMessage(passwordStr, getString(R.string.input_warn_password))) return
        //MD5加密
        passwordStr = MD5Utils.MD5Encode(passwordStr + userCodeStr)
        Logger.i("账号：$userCodeStr\n密码：$passwordStr")
        val tradingRequest = TradingRequest()
        tradingRequest.addHeader(SERVICE_CODE,LOGIN_TRADING_CODE)//交易号
                .addBody(LOGIN_USER_CODE , userCodeStr)//账号
                .addBody(LOGIN_PASSWORD , passwordStr)//密码
        Logger.t("json").w(getJson(tradingRequest))
        TradingTool.commitTrading(getJson(tradingRequest)).subscribe({result ->
            Logger.t("返回结果").i(result)
        })
    }

    /**
     * 提示内容(同时验证输入框内容是否为空)
     */
    private fun loginHintMessage(inputData: String, hintMessage: String): Boolean {
        if (TextUtils.isEmpty(inputData)) {
            ShowHint.warn(this, hintMessage)
            return true
        }
        return false
    }

    /**
     * 初始化
     */
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_login)//添加布局
        setSwipeBackEnable(false)//关闭滑动退出功能
        loginBtn.setOnClickListener(this)
        inputUserCode.setText("111111")
        inputPassWord.setText("222222")
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
