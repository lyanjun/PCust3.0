package com.c3.library.view.title;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作者： LYJ
 * 功能：
 * 创建日期： 2017/11/9
 */

public abstract class CustomTitleChild extends FrameLayout implements IsTitleChildView, View.OnClickListener {
    private static final int DEFAULT_TEXT_COLOR = 0xff000000;//默认字体颜色
    private static final int DEFAULT_TEXT_SIZE = 16;//默认字体大小
    private ViewGroup childGroup;//1级子控件，容器
    private int allTextColor = DEFAULT_TEXT_COLOR, allTextSize = DEFAULT_TEXT_SIZE;
    private ColorStateList allColorStateList;

    public CustomTitleChild(@NonNull Context context) {
        this(context, null);
    }

    public CustomTitleChild(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleChild(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        //获取子容器
        if (getChildCount() != 0) {
            childGroup = (ViewGroup) getChildAt(0);
        }
    }

    /**
     * 初始化子布局
     *
     * @param context
     */
    protected abstract void initView(Context context);

    /**
     * 设置指定位置的文字内容
     *
     * @param charSequence
     * @return
     */
    @Override
    public IsTitleChildView setChildText(CharSequence charSequence, int childIndex) {
        View childView = childGroup.getChildAt(childIndex);
        if (childView instanceof TextView) {
            ((TextView) childView).setText(charSequence);
        }
        return this;
    }

    /**
     * 批量设置字体颜色
     *
     * @param textColor
     * @return
     */
    @Override
    public IsTitleChildView setChildTextColor(int textColor) {
        this.allTextColor = textColor;
        return this;
    }

    /**
     * 批量设置字体大小
     *
     * @param textSize
     * @return
     */
    @Override
    public IsTitleChildView setChildTextSize(int textSize) {
        this.allTextSize = textSize;
        return this;
    }

    /**
     * 为指定的图片控件设置要显示的效果
     *
     * @param drawableRes
     * @param childIndex
     * @return
     */
    @Override
    public IsTitleChildView setChildButtonImage(int drawableRes, int childIndex) {
        //将指定位置的图片改变
        View childView = childGroup.getChildAt(childIndex);
        if (childView instanceof ImageView) {
            ((ImageView) childView).setImageDrawable(ContextCompat.getDrawable(getContext(), drawableRes));
        }
        return this;
    }

    /**
     * 批量设置字体颜色(点击效果)
     *
     * @param textColorList
     * @return
     */
    @Override
    public IsTitleChildView setChildTextColorList(@ColorRes int textColorList) {
        allColorStateList = ContextCompat.getColorStateList(getContext(), textColorList);
        return this;
    }

    /**
     * 批量更改
     *
     * @return
     */
    @Override
    public IsTitleChildView toChangeAllText() {
        for (int i = 0; i < childGroup.getChildCount(); i++) {
            View childView = childGroup.getChildAt(i);
            if (childView instanceof TextView) {
                ((TextView) childView).setTextSize(TypedValue.COMPLEX_UNIT_SP, allTextSize);
                if (null == allColorStateList) {
                    ((TextView) childView).setTextColor(allTextColor);
                } else {
                    ((TextView) childView).setTextColor(allColorStateList);
                }
            }
        }
        return this;
    }

    /**
     * 返回控件的引用对象
     *
     * @return
     */
    @Override
    public View getSelf() {
        return this;
    }

    /**
     * private int onclickTag;
     */
    private int onGroupChildClickTag;//回调识别标识
    private OnChildClickListener onChildClickListener;

    /**
     * 回调接口
     */
    public interface OnChildClickListener {
        //子控件标识可创建一个静态类，存放ID
        void onChildClick(int parentTag, int childTag);
    }

    /**
     * 添加监听
     *
     * @param onChildClickListener
     * @return
     */
    @Override
    public IsTitleChildView addOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
        return this;
    }

    /**
     * 设置标识
     */
    public final void setGroupTag(int groupTag) {
        this.onGroupChildClickTag = groupTag;
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (null != v.getTag() && null != onChildClickListener) {
            onChildClickListener.onChildClick(onGroupChildClickTag, (Integer) v.getTag());
        }
    }

    /**
     * 给子控件设置点击事件
     *
     * @param v
     * @param childTag
     * @param <V>
     */
    protected <V extends View> void setViewOnclickListener(V v, int childTag) {
        v.setTag(childTag);
        v.setOnClickListener(this);
    }
}
