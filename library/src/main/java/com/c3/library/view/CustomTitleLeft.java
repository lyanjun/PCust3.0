package com.c3.library.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.c3.library.R;
import com.c3.library.constant.TitleChildTag;

/**
 * 作者： LYJ
 * 功能： 默认标题栏左侧(有待完善)
 * 创建日期： 2017/11/9
 */

public class CustomTitleLeft extends CustomTitleChild {

    public CustomTitleLeft(@NonNull Context context) {
        super(context);
    }

    public CustomTitleLeft(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTitleLeft(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView(Context context) {
        inflate(context, R.layout.view_default_title_child_left_group, this);
        setViewOnclickListener(findViewById(R.id.default_back_btn), TitleChildTag.BACK_BTN);
    }
}
