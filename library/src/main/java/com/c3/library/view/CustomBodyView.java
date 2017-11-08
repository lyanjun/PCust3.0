package com.c3.library.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

import com.c3.library.R;

/**
 * 作者： LYJ
 * 功能： 界面的主题布局
 * 创建日期： 2017/11/8
 */

public class CustomBodyView extends ConstraintLayout {

    private View mBodyView, mTitleView;

    public CustomBodyView(Context context) {
        super(context, null);
    }

    public CustomBodyView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomBodyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 添加布局
     *
     * @param bodyID
     */
    public CustomBodyView initBodyView(int bodyID) {
        if (null == mBodyView) {
            mBodyView = inflate(getContext(), bodyID, null);
        }
        return this;
    }

    /**
     * 添加布局
     *
     * @param titleView
     */
    public CustomBodyView initTitleView(IsTitleView titleView , Integer height) {
        if (null == mTitleView) {
            mTitleView = titleView.getSelf();
            mTitleView.setId(R.id.title_bar);//未标题栏设置标记
            mTitleView.setTag(height);
        }
        return this;
    }

    public void combination(TitleShowType type) {
        switch (type) {
            case ARRANGE:
                addView(mBodyView,arrangeLayoutParams());
                addView(mTitleView,titleLayoutParams());
                break;
            case FLOATING:
                addView(mBodyView,floatingLayoutParams());
                addView(mTitleView,titleLayoutParams());
                break;
            case NONE:
                mTitleView = null;
                addView(mBodyView,floatingLayoutParams());
                break;
        }
    }

    /**
     * 主体位置
     * @return
     */
    private LayoutParams arrangeLayoutParams() {
        LayoutParams layoutParams = fixationExcludeLayoutParams();
        layoutParams.topToBottom = R.id.title_bar;
        return  layoutParams;
    }
    /**
     * 主体位置
     * @return
     */
    private LayoutParams floatingLayoutParams() {
        LayoutParams layoutParams = fixationExcludeLayoutParams();
        layoutParams.topToTop = LayoutParams.PARENT_ID;
        return layoutParams;
    }

    private LayoutParams fixationExcludeLayoutParams(){
        LayoutParams layoutParams = new LayoutParams(0, 0);
        layoutParams.bottomToBottom = LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = LayoutParams.PARENT_ID;
        layoutParams.rightToRight = LayoutParams.PARENT_ID;
        return layoutParams;
    }
    /**
     * 标题栏显示位置
     * @return
     */
    private LayoutParams titleLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(0, (Integer) mTitleView.getTag());
        layoutParams.leftToLeft = LayoutParams.PARENT_ID;
        layoutParams.rightToRight = LayoutParams.PARENT_ID;
        return layoutParams;
    }

    public enum TitleShowType {
        ARRANGE, FLOATING, NONE
    }
}
