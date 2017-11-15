package com.c3.library.view.title;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.c3.library.R;
import com.c3.library.constant.TitleChildTag;

/**
 * 作者： LYJ
 * 功能： 标题栏右侧(示例)
 * 创建日期： 2017/11/9
 */

public class CustomTitleRight extends CustomTitleChild implements View.OnClickListener{

    public CustomTitleRight(@NonNull Context context) {
        super(context);
    }

    public CustomTitleRight(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTitleRight(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView(Context context) {
        inflate(context, R.layout.view_default_title_child_right_group,this);
        setViewOnclickListener(findViewById(R.id.default_title_text), TitleChildTag.RIGHT_BTN);
    }
}
