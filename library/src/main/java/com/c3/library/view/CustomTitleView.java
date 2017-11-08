package com.c3.library.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c3.library.R;

/**
 * 作者： LYJ
 * 功能： 尝试以最小层级结构创建标题栏
 * 创建日期： 2017/11/8
 */

public class CustomTitleView extends ConstraintLayout implements IsTitleView{

    public CustomTitleView(Context context) {
        super(context , null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View getSelf() {
        return this;
    }

    /**
     * 右侧
     * @param view
     */
    @Override
    public void initLeftView(View view) {
        addView(view,matchParentHeight());
    }

    /**
     * 右侧
     * @param view
     */
    @Override
    public void initRightView(View view) {
        LayoutParams layoutParams = matchParentHeight();
        layoutParams.rightToRight = LayoutParams.PARENT_ID;
        addView(view,layoutParams);
    }

    /**
     * 中间
     * @param view
     */
    @Override
    public void initCenterView(View view) {
        LayoutParams layoutParams = matchParentHeight();
        layoutParams.leftToLeft = LayoutParams.PARENT_ID;
        layoutParams.rightToRight = LayoutParams.PARENT_ID;
        addView(view,layoutParams);
    }

    /**
     * 充满父布局的高度
     * @return
     */
    private LayoutParams matchParentHeight(){
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,0);
        layoutParams.topToTop = LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = LayoutParams.PARENT_ID;
        return layoutParams;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(300,500);
    }
}
