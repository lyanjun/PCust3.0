package com.c3.library.view.title;

import android.view.View;
import android.widget.RelativeLayout;

/**
 * 标题栏操作类型
 * Created by Lyan on 17/11/8.
 */

public interface IsTitleView {
    int CENTER = RelativeLayout.CENTER_IN_PARENT;
    int RIGHT = RelativeLayout.ALIGN_PARENT_RIGHT;
    int LEFT = RelativeLayout.ALIGN_PARENT_LEFT;
    View getSelf();//获取引用对象
    IsTitleView addChildView(IsTitleChildView view , int addSite);//添加子控件
}
