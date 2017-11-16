package com.c3.pcust30.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.c3.library.utils.MD5Utils
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import com.c3.pcust30.bean.net.*
import com.c3.pcust30.bean.net.rep.TradingRequest
import com.c3.pcust30.bean.net.rsp.TradingResponse
import com.c3.pcust30.bean.net.rsp.body.LoginRsp
import com.c3.pcust30.http.config.LOGIN_TRADING_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 作者： LYJ
 * 功能： 登录界面
 * 创建日期： 2017/11/7
 */
class LoginActivity : BaseActivity(), View.OnClickListener {
    /**
     * 点击事件(登录按钮，忘记密码，手势登录)
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
        loadHelper.showDialog()//展示加载弹窗
        val userCodeStr = inputUserCode.text.toString().trim()//用户账号
        var passwordStr = inputPassWord.text.toString().trim()//用户密码
        if (loginHintMessage(userCodeStr, getString(R.string.input_warn_user_code))) return
        if (loginHintMessage(passwordStr, getString(R.string.input_warn_password))) return
        //MD5加密
        passwordStr = MD5Utils.MD5Encode(passwordStr + userCodeStr)
        Logger.t(TAG).i("账号：$userCodeStr\n密码：$passwordStr")
        val tradingJson = getJson(TradingRequest().addHeader(SERVICE_CODE, LOGIN_TRADING_CODE)//交易号
                .addBody(LOGIN_USER_CODE, userCodeStr).addBody(LOGIN_PASSWORD, passwordStr))//账号,密码
        Logger.t(TAG).w("登录请求Json：$tradingJson")//提交内容
        //提交用户名密码
        TradingTool.commitTrading(tradingJson).bindToLifecycle(this)
                .doFinally { loadHelper.hideDialog() } //请求结束后关闭弹窗
                .subscribe({ result -> getResponse(result) }, { error -> ShowHint.failure(this, error.message!!) })
    }

    /**
     * 解析
     */
    override fun getResponse(result: String, tag: Int) {
        Logger.t(TAG).i("返回Json$result")
        Logger.t(TAG).json(result)//以JSON格式打印数据
        val objType = object : TypeToken<TradingResponse<LoginRsp>>() {}.type//解析类型
        val loginResponse = Gson().fromJson<TradingResponse<LoginRsp>>(result, objType)//解析结果
        if (TextUtils.equals(TRADING_SUCCESS, loginResponse.header!!.rspCode!!)) {
            ShowHint.success(this,loginResponse.header!!.rspMsg!!)
        } else {
            ShowHint.failure(this,loginResponse.header!!.rspMsg!!)
        }
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
