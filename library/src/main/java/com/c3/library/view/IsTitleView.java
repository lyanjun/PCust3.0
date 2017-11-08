package com.c3.library.view;

import android.view.View;

/**
 * 标题栏操作类型
 * Created by Lyan on 17/11/8.
 */

public interface IsTitleView {
    View getSelf();//获取引用对象
    void initLeftView(View view);
    void initRightView(View view);
    void initCenterView(View view);
}
