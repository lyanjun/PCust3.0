package com.c3.library.weight.load.impl;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.c3.library.R;
import com.c3.library.weight.load.CustomLoadDialog;
import com.orhanobut.logger.Logger;


/**
 * 作者： LYJ
 * 功能： 默认提示弹窗(圆形帧动画展示加载等待效果)
 * 创建日期： 2017/11/8
 */

public class DefaultLoadingDialog extends CustomLoadDialog{
    private AnimationDrawable animationDrawable;
    private TextView hintText;
    /**
     * 使用默认样式
     * @param context
     */
    public DefaultLoadingDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 设置布局
     * @return
     */
    @Override
    protected int showViewLayoutId() {
        return R.layout.dialog_default_loading;
    }

    /**
     * 初始化设置
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animationDrawable = (AnimationDrawable) ((ImageView)findViewById(R.id.default_loading)).getDrawable();
        hintText = findViewById(R.id.default_hint_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i("start");
        animationDrawable.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i("stop");
        animationDrawable.stop();
    }

    @Override
    public void onLoadingProgress() {
        super.onLoadingProgress();
        hintText.setText(R.string.default_load_hint_text_loading);
    }

    @Override
    public void onLoadingSuccess() {
        super.onLoadingSuccess();
        hintText.setText(R.string.default_load_hint_text_success);
    }

    @Override
    public void onLoadingFailure() {
        super.onLoadingFailure();
        hintText.setText(R.string.default_load_hint_text_failure);
    }
}
