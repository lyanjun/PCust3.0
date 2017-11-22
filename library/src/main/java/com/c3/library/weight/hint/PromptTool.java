package com.c3.library.weight.hint;

import android.support.annotation.Nullable;

import com.c3.library.weight.hint.listener.OnConfirmListener;
import com.c3.library.weight.hint.listener.impl.OnlyConfirmListenerImpl;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.config.ConfigBean;

/**
 * 提示窗
 * Created by Lyan on 17/11/20.
 */

public class PromptTool {
    /**
     * 获取全局配置对象
     * @return
     */
    public static PromptConfig config(){
        return PromptConfig.getInstance();
    }

    /**
     * 配置
     */
    public static ConfigBean hintWithConfirmBtn(@Nullable CharSequence title, CharSequence message , @Nullable OnConfirmListener confirmListener) {
        PromptConfig config = config();
        return StyledDialog.buildIosAlert(null == title ? "":title, message, new OnlyConfirmListenerImpl(confirmListener))
                .setHasShadow(config.isHasShadow())
                .setWidthPercent(config.getWidthPercent())
                .setBtnText(config.getConfirmBtnTxt());
    }
}
