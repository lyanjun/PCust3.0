package com.c3.pcust30.activity

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import com.c3.library.constant.SceneType
import com.c3.library.weight.hint.listener.OnConfirmListener
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.InitializePasswordRsp
import com.c3.pcust30.http.config.FORGET_PASSWORD_TRADING_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.c3.pcust30.tools.hintWithConfirmBtn
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.view_confirm_btn.view.*
import kotlinx.android.synthetic.main.view_left_title_right_input_single_line_verify.view.*
import kotlinx.android.synthetic.main.view_vertical_title.view.*

/**
 * 作者： LYJ
 * 功能： 忘记密码界面(使密码恢复到初始密码状态)
 * 创建日期： 2017/11/7
 */
class ForgetPasswordActivity : BaseActivity() {
    /**
     * 初始化
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_forget_password)
        confirmBtn.confirmBtnTxt.text = getString(R.string.forget_commit_btn_txt)
        userCode.inputTitle.text = getString(R.string.forget_input_user_code_txt)
        userTel.inputTitle.text = getString(R.string.forget_input_user_password_txt)
        userCode.inputDomain.hint = getString(R.string.forget_input_user_code_txt_hint)
        userTel.inputDomain.hint = getString(R.string.forget_input_user_password_txt_hint)
        verifyCodeArea.inputTitle.text = getString(R.string.forget_input_verify_code_txt)
        verifyCodeArea.inputDomain.hint = getString(R.string.forget_input_verify_code_txt_hint)
        //点击提交按钮
        confirmBtn.setOnClickListener { commitChangePassword() }
    }

    /**
     * 提交修改密码请求
     */
    private fun commitChangePassword() {
        val inputUserCode = userCode.inputDomain.trimmedString//用户名
        val inputUserTel = userTel.inputDomain.trimmedString//后台储存的联系方式
        val inputVerifyCode = verifyCodeArea.inputDomain.trimmedString//输入的验证码
        if (inputUserCode.isBlank()) {
            ShowHint.warn(this, getString(R.string.user_code_null_is_error))
            return
        }
        if (inputUserTel.isBlank()) {
            ShowHint.warn(this, getString(R.string.user_tel_null_is_error))
            return
        }
        if (inputVerifyCode.isBlank()) {
            ShowHint.warn(this, getString(R.string.verify_code_null_is_error))
            return
        }
        //验证验证码输入输入是否正确
        if (verifyCodeArea.verifyCodeView.isEqualsIgnoreCase(inputVerifyCode)) {
            //验证成功，提交初始化密码的请求
            loadHelper.showDialog()//显示弹窗
            val tradingJson = getJson(TradingRequest().addHeader(SERVICE_CODE, FORGET_PASSWORD_TRADING_CODE)
                    .addBody(LOGIN_USER_NAME, inputUserCode)
                    .addBody(LOGIN_USER_PHONE, inputUserTel))
            Logger.t(TAG).w("忘记密码重置请求Json：$tradingJson")//提交内容
            //提交请求
            TradingTool.commitTrading(tradingJson).bindToLifecycle(this)
                    .doFinally({ loadHelper.hideDialog() })
                    .subscribe({ result -> getResponse(result) }, { error -> ShowHint.failure(this, error.message!!) })
        } else {
            verifyCodeArea.verifyCodeView.refresh()//重置验证码
            ShowHint.warn(this, getString(R.string.verify_code_check_failure))
        }
    }

    /**
     * 处理请求结果
     */
    @Suppress("DEPRECATION")
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        val objType = object : TypeToken<TradingResponse<InitializePasswordRsp>>() {}.type//解析类型
        val loginResponse = Gson().fromJson<TradingResponse<InitializePasswordRsp>>(result, objType)//解析结果
        if (TextUtils.equals(TRADING_SUCCESS, loginResponse.header!!.rspCode)) {
            hintWithConfirmBtn(getString(R.string.initialize_password_success_title),
                    Html.fromHtml(String.format(getString(R.string.initialize_password_success),
                            loginResponse.body!!.defaultpwd!!.defaultpwd)), OnConfirmListener {
                setResult(RESULT_OK)//返回主界面
                finish()//关闭界面
            }).show()
        } else {
            ShowHint.warn(this, loginResponse.header!!.rspMsg!!)
        }
    }

    /**
     * 设置退出动画效果
     */
    override fun setTheSceneType(): Int = SceneType.CUSTOM_TYPE

    override fun setTitleText(): CharSequence = getString(R.string.act_title_forget_password)
}
