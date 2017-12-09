package com.c3.pcust30.top

import android.content.Context

/**
 * 作者： LYJ
 * 功能： 一些常用的方法
 * 创建日期： 2017/12/9
 */
/**
 * 获取图片的资源ID
 */
fun getDrawableResId(resName: String, context: Context): Int {
    val applicationContext = context.applicationContext
    return applicationContext.resources.getIdentifier(resName, "drawable", applicationContext.packageName)
}

