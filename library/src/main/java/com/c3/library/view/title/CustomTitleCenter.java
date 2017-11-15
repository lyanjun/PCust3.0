package com.c3.library.view.title;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.c3.library.R;

/**
 * 作者： LYJ
 * 功能： 显示标题基本没有变化
 * 创建日期： 2017/11/9
 */

public class CustomTitleCenter extends CustomTitleChild {
    public CustomTitleCenter(@NonNull Context context) {
        super(context);
    }

    public CustomTitleCenter(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTitleCenter(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView(Context context) {
        inflate(context, R.layout.view_default_title_child_center_group,this);
    }
}
