package com.c3.library.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c3.library.view.adapter.SmallVerticalAdapter;

/**
 * 适用于数据展示不多的列表结构
 * (用于嵌套在可滑动布局中)
 * (有待拓展)
 * Created by Lyan on 17/12/1.
 */

public class SmallVerticalLayout extends ViewGroup {
    private int s_childCount;//子控件的个数
    private View headerView;//头部

    public SmallVerticalLayout(Context context) {
        super(context);
    }

    public SmallVerticalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmallVerticalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量在摆放之前
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int onMeasureWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        s_childCount = getChildCount();
        int onMeasureHeight = 0;
        for (int i = 0; i < s_childCount; i++) {
            onMeasureHeight += getChildAt(i).getLayoutParams().height;
        }
        setMeasuredDimension(onMeasureWidth, onMeasureHeight);
    }

    /**
     * 测量结束后再设置子控件的测量模式
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        for (int i = 0; i < s_childCount; i++) {
            View childView = getChildAt(i);
            int childHeight = childView.getLayoutParams().height;
            //给子控件设置宽高度
            int childWidthMeasureSpec = getChildMeasureSpec(0, 0, w);
            int childHeightMeasureSpec = getChildMeasureSpec(0, 0, childHeight);
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    /**
     * 摆放子控件
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int startHeight = 0;
        for (int i = 0; i < s_childCount; i++) {
            View child = getChildAt(i);
            int nowChildHeight = child.getLayoutParams().height;
            Log.w("header", nowChildHeight + "");
            child.layout(l, startHeight, r, startHeight + nowChildHeight);
            startHeight += nowChildHeight;
        }
    }

    /**
     * 设置适配器
     *
     * @param verticalAdapter
     */
    public void setAdapter(SmallVerticalAdapter verticalAdapter) {
        if (getChildCount() >= verticalAdapter.getItemCounts()) return;
        for (int i = 0; i < verticalAdapter.getItemCounts(); i++) {
            View child = verticalAdapter.createItemView(this);
            verticalAdapter.getItemView(child, i);
            addView(child);
        }
    }

    /**
     * 添加头部
     *
     * @param headLayout
     */
    public View addHeadView(@LayoutRes int headLayout) {
        if (null == headerView) {
            headerView = LayoutInflater.from(getContext()).inflate(headLayout, this, false);
            LayoutParams headerLayoutParams = headerView.getLayoutParams();
            if (headerLayoutParams.height == LayoutParams.WRAP_CONTENT) {
                headerLayoutParams.height = headerView.getMinimumHeight();
            }
            addView(headerView, 0);
        }
        return headerView;
    }
}
