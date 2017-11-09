package com.c3.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.c3.library.R;

/**
 * 作者： LYJ
 * 功能： 尝试以最小层级结构创建标题栏
 * 创建日期： 2017/11/8
 */

public class CustomTitleView extends RelativeLayout implements IsTitleView {

    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.view_default_title_bar, this);
    }

    /**
     * 获取控件
     *
     * @return
     */
    @Override
    public View getSelf() {
        return this;
    }

    /**
     * 添加子控件
     *
     * @param titleChildView
     */
    @Override
    public IsTitleView addChildView(IsTitleChildView titleChildView, int addSite) {
        if (null != titleChildView){
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(addSite);
            addView(titleChildView.getSelf(), layoutParams);
        }
        return this;
    }
}
