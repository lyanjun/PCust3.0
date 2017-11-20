package com.c3.library.weight.hint;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

/**
 * 提示窗
 * Created by Lyan on 17/11/20.
 */

public class PromptManger {

    private PromptManger() {
    }

    private static class SingleTon {
        private final static PromptManger INSTANCE = new PromptManger();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static PromptManger getInstance() {
        return SingleTon.INSTANCE;
    }

    /**
     * 配置
     */
    private float widthPercentage = 0.9f;//宽度百分比
    private float heightPercentage = 0.9f;//高度百分比

    public void hint(CharSequence title, CharSequence message) {
        StyledDialog.buildIosAlert(title, message, new MyDialogListener() {
            @Override
            public void onFirst() {

            }

            @Override
            public void onSecond() {

            }
        }).setWidthPercent(widthPercentage)
                .setHeightPercent(heightPercentage);
    }
}
