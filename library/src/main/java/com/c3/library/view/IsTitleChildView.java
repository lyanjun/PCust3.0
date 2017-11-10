package com.c3.library.view;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * 作者： LYJ
 * 功能： 标题栏中的子控件
 * 创建日期： 2017/11/9
 */

public interface IsTitleChildView {
    View getSelf();//获取引用对象
    IsTitleChildView setChildText(CharSequence charSequence, int childIndex);//设置文字
    IsTitleChildView setChildTextSize(int textSize);//设置文字大小
    IsTitleChildView setChildTextColor(int textColor);//设置文字颜色
    IsTitleChildView setChildTextColorList(@ColorRes int textColorList);//设置文字颜色
    IsTitleChildView setChildButtonImage(@DrawableRes int drawableRes , int childIndex);//设置按钮的图片
    IsTitleChildView toChangeAllText();//通知发生变化
    IsTitleChildView addOnChildClickListener(CustomTitleChild.OnChildClickListener onChildClickListener);
}
