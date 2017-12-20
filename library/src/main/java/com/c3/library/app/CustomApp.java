package com.c3.library.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * 作者： LYJ
 * 功能： 应用入口
 * 创建日期： 2017/11/7
 */

public class CustomApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化工具类
        Utils.init(this);
    }
}
