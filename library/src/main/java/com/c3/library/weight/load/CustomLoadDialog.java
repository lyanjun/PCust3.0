package com.c3.library.weight.load;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.c3.library.R;
import com.c3.library.listener.OnLoadingData;

/**
 * 作者： LYJ
 * 功能： 播放帧动画的加载弹窗
 * 创建日期： 2017/11/8
 */

public abstract class CustomLoadDialog extends Dialog implements OnLoadingData {
    /**
     * 使用默认的属性
     *
     * @param context
     */
    public CustomLoadDialog(@NonNull Context context) {
        super(context, R.style.Custom_Dialog_Theme_Background_DimEnabled_False);
    }

    /**
     * 设置弹窗的效果样式
     *
     * @param context
     * @param themeResId
     */
    public CustomLoadDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(showViewLayoutId());
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;//默认居中
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.PopFadeAnim);
        setCanceledOnTouchOutside(false);
    }

    protected abstract int showViewLayoutId();


    @Override
    public void onLoadingProgress() {
    }

    @Override
    public void onLoadingSuccess() {

    }

    @Override
    public void onLoadingFailure() {

    }
}
