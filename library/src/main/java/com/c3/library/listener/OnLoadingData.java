package com.c3.library.listener;

/**
 * 作者： LYJ
 * 功能： 加载数据中
 * 创建日期： 2017/11/7
 */

public interface OnLoadingData {
    void onLoadingProgress();//加载过程中

    void onLoadingSuccess();//加载成功

    void onLoadingFailure();//加载失败
}
