package com.c3.pcust30.tools

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView

import java.util.ArrayList

/**
 * 圆圈的背景颜色
 * Created by liyanjun on 2017/6/8.
 */

object CircleColorUtils {
    private val COLOR_ARRAY_SIZE = circleColorList.size//数组长度
    /**
     * 颜色集合
     * @return
     */
    private val circleColorList: List<Int>
        get() {
            val colorList = ArrayList<Int>(COLOR_ARRAY_SIZE)
            colorList.add(Color.rgb(152, 142, 18))
            colorList.add(Color.rgb(121, 125, 249))
            colorList.add(Color.rgb(252, 98, 53))
            colorList.add(Color.rgb(254, 192, 52))
            colorList.add(Color.rgb(235, 129, 35))
            colorList.add(Color.rgb(181, 81, 96))
            colorList.add(Color.rgb(252, 177, 66))
            colorList.add(Color.rgb(170, 80, 81))
            colorList.add(Color.rgb(71, 37, 42))
            colorList.add(Color.rgb(199, 66, 13))
            colorList.add(Color.rgb(152, 142, 18))
            colorList.add(Color.rgb(133, 166, 254))
            colorList.add(Color.rgb(100, 188, 13))
            colorList.add(Color.rgb(250, 12, 13))
            colorList.add(Color.rgb(99, 81, 17))
            colorList.add(Color.rgb(209, 71, 99))
            return colorList
        }

    /**
     * 获取颜色数组
     * @return
     */
    private val circleColorArray: Array<Int>
        get() = circleColorList.toTypedArray()

    /**
     * 设置背景颜色
     */
    fun setCircleBackgroundColor(values : Int, drawable: GradientDrawable) =
            drawable.setColor(circleColorArray[values%COLOR_ARRAY_SIZE])

    /**
     * 设置商户图标的背景颜色
     */
    fun setCircleBackgroundColor(drawable: GradientDrawable) =
            drawable.setColor(Color.parseColor("#EF6C13"))
}

