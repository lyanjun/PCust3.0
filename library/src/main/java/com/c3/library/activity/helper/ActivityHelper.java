package com.c3.library.activity.helper;

import android.app.Activity;

import com.c3.library.listener.OnLoadingData;
import com.orhanobut.logger.Logger;

/**
 * 作者： LYJ
 * 功能： 实现相关的设置
 * 创建日期： 2017/11/7
 */

public class ActivityHelper implements OnLoadingData{

    private Activity activity;

    public ActivityHelper(Activity activity) {
        this.activity = activity;
    }

    /**
     * 下载中
     */
    @Override
    public void onLoadingProgress() {
        Logger.i("过程");
    }

    /**
     * 下载成功
     */
    @Override
    public void onLoadingSuccess() {
        Logger.i("成功");
    }

    /**
     * 下载失败
     */
    @Override
    public void onLoadingFailure() {
        Logger.i("失败");
    }
}
