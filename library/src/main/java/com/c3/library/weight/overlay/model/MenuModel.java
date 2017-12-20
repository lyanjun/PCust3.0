package com.c3.library.weight.overlay.model;

import android.support.annotation.DrawableRes;

/**
 * 作者： LYJ
 * 功能： 菜单数据模型
 * 创建日期： 2017/12/20
 */

public class MenuModel {
    private String menuName;//菜单标题
    private String menuType;//菜单类型
    @DrawableRes
    private int drawableRes;//菜单项图标

    public MenuModel(String menuName, String menuType, int drawableRes) {
        this.menuName = menuName;
        this.menuType = menuType;
        this.drawableRes = drawableRes;
    }

    public MenuModel(String menuName, int drawableRes) {
        this.menuName = menuName;
        this.drawableRes = drawableRes;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }
}
