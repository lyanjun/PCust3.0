package com.c3.library.weight.hint;

/**
 * 作者： LYJ
 * 功能： 配置(对外开放，全局配置)
 * 创建日期： 2017/11/21
 */
//todo 有待拓展
public class PromptConfig {

    private PromptConfig() {}

    private static class SingleTon {
        private final static PromptConfig INSTANCE = new PromptConfig();
    }
    /**
     * 获取单例
     *
     * @return
     */
     static PromptConfig getInstance() {
        return PromptConfig.SingleTon.INSTANCE;
    }


    /**********************************************************************************************
     * 配置
     **********************************************************************************************/

    private boolean hasShadow = false;//是否有阴影
    private float widthPercent = 0.9f;//设置弹窗宽度的百分比
    private CharSequence confirmBtnTxt = "confirm" , cancelBtnTxt = "cancel";//确定按钮和取消按钮的显示的文字


    CharSequence getConfirmBtnTxt() {
        return confirmBtnTxt;
    }

    public PromptConfig setConfirmBtnTxt(CharSequence confirmBtnTxt) {
        this.confirmBtnTxt = confirmBtnTxt;
        return this;
    }

    CharSequence getCancelBtnTxt() {
        return cancelBtnTxt;
    }

    public PromptConfig setCancelBtnTxt(CharSequence cancelBtnTxt) {
        this.cancelBtnTxt = cancelBtnTxt;
        return this;
    }

    boolean isHasShadow() {
        return hasShadow;
    }

    public PromptConfig setHasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
        return this;
    }

    float getWidthPercent() {
        return widthPercent;
    }

    public PromptConfig setWidthPercent(float widthPercent) {
        this.widthPercent = widthPercent;
        return this;
    }
}
