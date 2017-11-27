package com.c3.library.helper;

import com.c3.library.weight.load.CustomLoadDialog;

/**
 * 作者： LYJ
 * 功能： 实现相关的设置
 * 创建日期： 2017/11/7
 */

public final class LoadDialogHelper {
    /**
     * 弹窗
     */
    private CustomLoadDialog customLoadDialog;

    /**
     * 默认构造
     */
    public LoadDialogHelper() {
    }

    /**
     * 设置要控制的弹窗
     *
     * @param customLoadDialog
     */
    public void setCustomLoadDialog(CustomLoadDialog customLoadDialog) {
        this.customLoadDialog = customLoadDialog;
    }

    /**
     * 获取要操作的对象
     *
     * @param animationLoadDialog
     */
    public LoadDialogHelper(CustomLoadDialog animationLoadDialog) {
        this.customLoadDialog = animationLoadDialog;
    }

    /**
     * 设置家在状态
     *
     * @param loadStatus
     */
    public void onLoadStatus(LoadStatus loadStatus) {
        switch (loadStatus) {
            case LOADING://下载中
                customLoadDialog.onLoadingProgress();
                break;
            case SUCCESS://下载成功
                customLoadDialog.onLoadingSuccess();
                break;
            case FAILURE://下载失败
                customLoadDialog.onLoadingFailure();
                break;
        }
    }

    /**
     * 展示弹窗
     */
    public void showDialog() {
        customLoadDialog.show();
        onLoadStatus(LoadStatus.LOADING);
    }
    /**
     * 展示弹窗
     */
    public void showDialogWithDismissFirst() {
        hideDialog();
        customLoadDialog.show();
        onLoadStatus(LoadStatus.LOADING);
    }
    /**
     * 隐藏弹窗
     */
    public void hideDialog() {
        if (customLoadDialog.isShowing())customLoadDialog.dismiss();
    }
}
