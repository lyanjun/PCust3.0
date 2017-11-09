package com.c3.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.c3.library.R;

/**
 * 作者： LYJ
 * 功能： 界面的主题布局
 * 创建日期： 2017/11/8
 */

public class CustomBodyView extends RelativeLayout {

    private View mBodyView, mTitleView;
    private TitleShowType titleShowType;

    public CustomBodyView(Context context) {
        super(context, null);
    }

    public CustomBodyView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomBodyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_default_root_group, this);
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
    public CustomBodyView initTitleView(IsTitleView titleView, Integer height) {
        if (null == mTitleView && titleShowType != TitleShowType.NONE) {
            mTitleView = titleView.getSelf();
            mTitleView.setId(R.id.mine_title_bar);//未标题栏设置标记
            mTitleView.setTag(height);
        }
        return this;
    }

    /**
     * 设置标题栏显示位置
     *
     * @param type
     * @return
     */
    public CustomBodyView setTitleShowType(TitleShowType type) {
        titleShowType = type;
        return this;
    }

    /**
     * 组合视图
     */
    public void combination() {
        switch (titleShowType) {
            case ARRANGE:
                addView(mBodyView, arrangeLayoutParams());
                addView(mTitleView, titleLayoutParams());
                break;
            case FLOATING:
                addView(mBodyView, floatingLayoutParams());
                addView(mTitleView, titleLayoutParams());
                break;
            case NONE:
                mTitleView = null;
                addView(mBodyView, floatingLayoutParams());
                break;
        }
    }

    /**
     * 主体位置
     *
     * @return
     */
    private LayoutParams arrangeLayoutParams() {
        LayoutParams layoutParams = matchParentLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, mTitleView.getId());
        return layoutParams;
    }

    /**
     * 主体位置
     *
     * @return
     */
    private LayoutParams floatingLayoutParams() {
        return matchParentLayoutParams();
    }


    private LayoutParams matchParentLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 标题栏显示位置
     *
     * @return
     */
    private LayoutParams titleLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, (Integer) mTitleView.getTag());
    }

    //排列、悬浮、不显示
    public enum TitleShowType {
        ARRANGE, FLOATING, NONE
    }
}
