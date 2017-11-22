package com.c3.library.weight.hint.listener.impl;

import com.c3.library.weight.hint.listener.OnConfirmListener;
import com.hss01248.dialog.interfaces.MyDialogListener;

/**
 * 作者： LYJ
 * 功能： 仅点击确认按钮的回调
 * 创建日期： 2017/11/21
 */

public class OnlyConfirmListenerImpl extends MyDialogListener{
    /**
     * 确认按钮的回调接口
     */
    private OnConfirmListener onConfirmListener;

    public OnlyConfirmListenerImpl(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    @Override
    public void onFirst() {
        if (null != onConfirmListener) onConfirmListener.onUserConfirm();
    }

    @Override
    public void onSecond() {}
}
