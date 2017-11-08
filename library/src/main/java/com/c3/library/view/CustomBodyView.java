package com.c3.library.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

/**
 * 作者： LYJ
 * 功能： 界面的主题布局
 * 创建日期： 2017/11/8
 */

public class CustomBodyView extends ConstraintLayout {

    public CustomBodyView(Context context) {
        super(context , null);
    }

    public CustomBodyView(Context context, AttributeSet attrs) {
        super(context, attrs , 0);
    }

    public CustomBodyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundColor(Color.RED);
    }
}
