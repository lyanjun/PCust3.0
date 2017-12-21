package com.c3.library.weight.overlay.listener;

import io.reactivex.annotations.NonNull;

/**
 * 作者： LYJ
 * 功能： 菜单选择回调
 * 创建日期： 2017/12/21
 */

public interface OnMenuSelectedListener {
    /**
     * 返回菜单类型
     * @param menuType
     */
    void onMenuSelected(@NonNull String menuType);
}
