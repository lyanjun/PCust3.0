package com.c3.pcust30.tools

import com.c3.library.weight.hint.PromptTool
import com.c3.library.weight.hint.listener.OnConfirmListener
import com.hss01248.dialog.config.ConfigBean

/**
 * 作者： LYJ
 * 功能： 提示(用来创建提示窗)
 * 创建日期： 2017/11/21
 */
fun hintWithConfirmBtn(title: CharSequence, message: CharSequence, confirmListener: OnConfirmListener? = null): ConfigBean {
    return PromptTool.hintWithConfirmBtn(title, message, confirmListener)
}

fun hintWithConfirmBtn(message: CharSequence, confirmListener: OnConfirmListener? = null): ConfigBean {
    return PromptTool.hintWithConfirmBtn(null, message, confirmListener)
}
