package com.c3.pcust30.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.c3.library.constant.SceneType
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.act.BaseActivity
import com.c3.pcust30.data.info.USER_NAME
import com.c3.pcust30.top.*
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import com.takwolf.android.lock9.Lock9View
import kotlinx.android.synthetic.main.activity_gesture_password.*

/**
 * 作者： LYJ
 * 功能： 手势密码界面(设置和解锁都在这个界面中)
 * 创建日期： 2017/11/7
 */
class GesturePasswordActivity : BaseActivity(), Lock9View.CallBack {
    private var gestureFunctionType: String? = null//用来区分功能的标识
    private var firstSetGesturePwd: String? = null//首次设置手势密码
    private var gestureSkipType: String? = null//跳转类型（判断跳转到那个界面）
    /**
     * 监听九宫格手势结果
     */
    override fun onFinish(password: String?) {
        Logger.t(TAG).i("${if (isSetGesturePwd()) "设置" else "登录"}$password")
        if (isSetGesturePwd()) {//设置手势密码
            if (null == firstSetGesturePwd) {
                if (password!!.length < 4) {
                    ShowHint.warn(this, getString(R.string.gesture_hint_init_length))
                    return
                }
                firstSetGesturePwd = password//第一次输入的手势密码
                gestureHintTv.text = getString(R.string.gesture_hint_init_pwd_again)//提示
                return
            }
            //第二次录入手势密码
            if (TextUtils.equals(firstSetGesturePwd, password)) {
                ShowHint.success(this, getString(R.string.gesture_hint_set_pwd_success))//提示
                //设置手势密码成功
                Hawk.put(GESTURE_PASSWORD, password)//保存手势密码(用来作为下次登录的验证信息)
                Hawk.put(GESTURE_LOGIN_STATUS, true)//设置手势登录未开启状态
                //todo 判断是修改（从主页开启该界面）还是设置（从登陆界面启动该界面）
                when (gestureSkipType) {//判断跳转到哪个界面
                //主界面
                    GESTURE_SKIP_TO_MAIN -> {
                        ShowHint.warn(this, "主界面")
                    }
                //重置密码界面
                    GESTURE_SKIP_TO_SET_PWD -> {
                        ShowHint.warn(this, "重置密码界面")
                    }
                }
            } else {
                firstSetGesturePwd = null
                gestureHintTv.text = getString(R.string.gesture_hint_init_pwd)
                ShowHint.warn(this, getString(R.string.gesture_hint_verify_init_pwd_failure))
            }
        } else {//使用手势密码登录
            if (TextUtils.equals(Hawk.get<String>(GESTURE_PASSWORD), password)) {
                gestureHintTv.text = getString(R.string.gesture_hint_verify_success)
                ShowHint.hint(this, getString(R.string.gesture_hint_verify_success))
                //todo 登录验证成功 跳转到首页（主界面）
            }else{
                gestureHintTv.text = getString(R.string.gesture_hint_verify_failure)
                ShowHint.warn(this, getString(R.string.gesture_hint_verify_failure))
            }
        }
    }

    /**
     * 初始化
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_gesture_password)
        gestureFunctionType = intent.getStringExtra(GESTURE_PASSWORD)//当前界面功能判断标识
        gestureSkipType = intent.getStringExtra(GESTURE_SKIP_TYPE)//跳转界面标识符
        loginUserName.text = getString(R.string.gesture_user_name).format(Hawk.get<String>(USER_NAME))//设置显示用户名称
        gestureLockView.setCallBack(this)//手势监听
        setViewShowWithFunctionType()//根据功能初始化视图显示效果
    }

    /**
     * 根据功能初始化视图显示效果
     */
    private fun setViewShowWithFunctionType() {
        if (isSetGesturePwd()) {//设置手势密码
            //提示用户初始化手势密码
            gestureHintTv.text = getString(R.string.gesture_hint_init_pwd)
        } else {//使用手势密码登录
            backToLoginBtn.visibility = View.VISIBLE//显示返回登录页按钮
        }
    }

    /**
     * 设置退出动画效果
     */
    override fun setTheSceneType(): Int {
        return SceneType.CUSTOM_TYPE
    }

    /**
     * 设置标题
     */
    override fun setTitleText(): CharSequence = if (isSetGesturePwd()) getString(R.string.act_title_gesture_set) else getString(R.string.act_title_gesture_use)

    /**
     * 判断功能状态
     */
    private fun isSetGesturePwd(): Boolean = TextUtils.equals(gestureFunctionType, SET_GESTURE_PASSWORD)
}
