package com.c3.pcust30.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import com.c3.library.constant.SceneType
import com.c3.library.utils.MD5Utils
import com.c3.library.weight.hint.listener.OnConfirmListener
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import com.c3.pcust30.data.info.ORG_CODE
import com.c3.pcust30.data.info.USER_CODE
import com.c3.pcust30.data.info.USER_PASSWORD
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.ResetPasswordRsp
import com.c3.pcust30.http.config.RESET_PASSWORD_TRADING_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.c3.pcust30.tools.hintWithConfirmBtn
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.view_confirm_btn.view.*
import kotlinx.android.synthetic.main.view_left_title_right_input_single_line_password.view.*
import kotlinx.android.synthetic.main.view_vertical_title.view.*

/**
 * 作者： LYJ
 * 功能： 重置密码界面(必修修改密码，后台数据不出问题的情况下，初始密码必须由用户更改)
 *        修改确认后跳转到主界面，此界面由用户状态来决定是否显示
 * 创建日期： 2017/11/7
 */
class ResetPasswordActivity : BaseActivity() {
    private val userCode = Hawk.get<String>(USER_CODE)//用户编码
    private val orgCode = Hawk.get<String>(ORG_CODE)//组织编码
    /**
     * 初始化界面
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_reset_password)
        setSwipeBackEnable(false)//不可滑动退出
        //初始化界面
        oldPassWordLayout.inputDomain.hint = getString(R.string.reset_pwd_input_hint_old_title_txt)
        newPassWordLayout.inputDomain.hint = getString(R.string.reset_pwd_input_hint_new_title_txt)
        confirmPassWordLayout.inputDomain.hint = getString(R.string.reset_pwd_input_hint_confirm_title_txt)
        oldPassWordLayout.inputTitle.text = getString(R.string.reset_pwd_input_old_title_txt)
        newPassWordLayout.inputTitle.text = getString(R.string.reset_pwd_input_new_title_txt)
        confirmPassWordLayout.inputTitle.text = getString(R.string.reset_pwd_input_confirm_title_txt)
        confirmBtn.confirmBtnTxt.text = getString(R.string.reset_confirm_btn_txt)
        confirmBtn.setOnClickListener { confirmChangePassword() }//点击确定，提交修改密码的Json格式的数据
    }

    /**
     * 确认修改密码
     */
    private fun confirmChangePassword() {
        var oldPassword = oldPassWordLayout.inputDomain.trimmedString//旧密码
        var newPassword = newPassWordLayout.inputDomain.text.toString()//新密码
        val confirmPassword = confirmPassWordLayout.inputDomain.text.toString()//确认密码
        if (TextUtils.isEmpty(oldPassword)) {
            ShowHint.warn(this, getString(R.string.new_password_null_is_error))
            return
        }
        if (TextUtils.isEmpty(newPassword)) {
            ShowHint.warn(this, getString(R.string.old_password_null_is_error))
            return
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            ShowHint.warn(this, getString(R.string.confirm_password_null_is_error))
            return
        }
        if (TextUtils.equals(oldPassword, newPassword)) {
            ShowHint.warn(this, getString(R.string.new_and_old_should_different))
            return
        }
        if (!TextUtils.equals(newPassword, confirmPassword)) {
            ShowHint.warn(this, getString(R.string.new_and_confirm_should_same))
            return
        }
        //开启等待弹窗

        //提交修改密码的请求
        loadHelper.showDialog()//显示等待弹窗
        oldPassword = MD5Utils.MD5Encode(oldPassword + userCode)
        newPassword = MD5Utils.MD5Encode(newPassword + userCode)
        //请求数据（json）
        val tradingJson = getJson(TradingRequest().addHeader(SERVICE_CODE, RESET_PASSWORD_TRADING_CODE)
                .addBody(LOGIN_USER_NAME, userCode)
                .addBody(RESET_OLD_PASSWORD, oldPassword)
                .addBody(RESET_NEW_PASSWORD, newPassword)
                .addBody(LOGIN_ORG_CODE, orgCode)
                .addBody(LOGIN_USER_CODE, userCode))
        Logger.t(TAG).w("重置密码请求Json：$tradingJson")//提交内容
        //提交请求
        TradingTool.commitTrading(tradingJson).bindToLifecycle(this)
                .doFinally({ loadHelper.hideDialog() })
                .subscribe({ result -> getResponse(result) }, { error -> ShowHint.failure(this, error.message!!) })
    }

    /**
     * 处理返回结果89
     */
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        val objType = object : TypeToken<TradingResponse<ResetPasswordRsp>>() {}.type//解析类型
        val resetResponse = Gson().fromJson<TradingResponse<ResetPasswordRsp>>(result, objType)//解析结果
        if (TextUtils.equals(resetResponse.header!!.rspCode, TRADING_SUCCESS)) {
            //保存密码到本地
            Hawk.put(USER_PASSWORD, confirmPassWordLayout.inputDomain.text.toString())//保存新密码
            //修改密码成功,显示弹窗 点击确定跳转到主界面
            hintWithConfirmBtn("修改密码成功！", OnConfirmListener {
                ShowHint.hint(this, "跳转到主界面")
            }).show().setCancelable(false)
        } else {
            ShowHint.warn(this, resetResponse.header!!.rspMsg!!)
        }
    }

    /**
     * 设置标题
     */
    override fun setTitleText(): CharSequence = getString(R.string.act_title_reset_password)

    /**
     * 设置退出动画效果
     */
    override fun setTheSceneType(): Int = SceneType.CUSTOM_TYPE

    /**
     * 无返回键
     */
    override fun setTitleLeftChildView() = null

    /**
     * 屏蔽返回键
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) return true
        return super.onKeyDown(keyCode, event)
    }
}
