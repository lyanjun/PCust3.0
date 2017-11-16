package com.c3.pcust30.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.c3.library.utils.MD5Utils
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import com.c3.pcust30.data.info.*
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.entity.WorkSignInfo
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.LoginRsp
import com.c3.pcust30.http.config.LOGIN_TRADING_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 作者： LYJ
 * 功能： 登录界面
 * 创建日期： 2017/11/7
 */
class LoginActivity : BaseActivity(), View.OnClickListener {

    private var isFirstStart: Boolean = true //判断是否为第一次启动的标记
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
        val userCodeStr = inputUserCode.text.toString().trim()//用户账号
        var passwordStr = inputPassWord.text.toString().trim()//用户密码
        if (loginHintMessage(userCodeStr, getString(R.string.input_warn_user_code))) return
        if (loginHintMessage(passwordStr, getString(R.string.input_warn_password))) return
        loadHelper.showDialog()//展示加载弹窗
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
            ShowHint.success(this, loginResponse.header!!.rspMsg!!)
            //将用户的信息储存到本地（作为免用户登陆的准备操作）
            val userInfo = loginResponse.body!!.worksigninfo!!
            isFirstStart = Hawk.get<Boolean>(IS_FIRST, true)
            if (isFirstStart) {//判断是否为首次启动
                //是首次启动则跳转到手势密码设置界面

            } else {//非首次启动逻辑处理
                when (userInfo.firstLogin) {
                //是首次登陆
                    FIRST_LOGIN_FALSE -> {
                        //进入到密码重置界面（引发条件，初次启动后到设置手势密码设置之后返回首页后
                        //在点击登陆按钮后则会跳转到该重置密码界面）
                    }
                //非首次登录
                    FIRST_LOGIN_TRUE -> {
                        //点击登陆按钮后直接跳转到主界面
                    }
                    else -> ShowHint.warn(this, userInfo.loginmsg!!)
                }
            }
            saveUserInfoToLocation(userInfo)//保存此次登陆的数据信息
        } else {
            ShowHint.failure(this, loginResponse.header!!.rspMsg!!)
        }
    }

    /**
     * 保存用户信息
     */
    private fun saveUserInfoToLocation(userInfo: WorkSignInfo) {
        Hawk.put(USER_ID, userInfo.userid!!)
        Hawk.put(USER_CODE, userInfo.usercode!!)
        Hawk.put(ORG_CODE, userInfo.orgcode!!)
        Hawk.put(LOGIN_CODE, userInfo.logincode!!)
        Hawk.put(LOGIN_MSG, userInfo.loginmsg!!)
        Hawk.put(FIRST_LOGIN, userInfo.firstLogin!!)
        Hawk.put(USER_NAME, userInfo.userName!!)
        Hawk.put(ID_NO, userInfo.idno!!)
        Hawk.put(ORG_NAME, userInfo.orgName!!)
        Hawk.put(PHONE, userInfo.phone!!)
        Hawk.put(IS_FIRST, false)//这个值只要是保存了，这个应用就不是首次打开了
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
