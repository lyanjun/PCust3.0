package com.c3.library.weight.toast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * 作者： LYJ
 * 功能： 提示
 * 创建日期： 2017/11/14
 */

public class ShowHint {
    /**
     * 成功
     * @param context
     * @param message
     */
    public static void success(Context context, @NonNull CharSequence message) {
        Toasty.success(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    /**
     * 提示
     * @param context
     * @param message
     */
    public static void hint(Context context, @NonNull CharSequence message) {
        Toasty.info(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    /**
     * 警告
     * @param context
     * @param message
     */
    public static void warn(Context context, @NonNull CharSequence message) {
        Toasty.warning(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    /**
     * 失败
     * @param context
     * @param message
     */
    public static void failure(Context context, @NonNull CharSequence message) {
        Toasty.error(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
