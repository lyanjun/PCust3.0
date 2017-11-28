package com.c3.library.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * 作者： LYJ
 * 功能： 实践工具类
 * 创建日期： 2017/11/28
 */

public class DataTools {
    public static final String YEAR_MONTH = "yyyyMM";//格式化类型

    /**
     * 往前补日期
     * @param addNum
     * @param startDate
     * @return
     */
    public static List<String> getPreviousMonths(int addNum, String startDate) {
        List<String> times = new ArrayList<>();
        DateTime dateTime = DateTime.parse(startDate, DateTimeFormat.forPattern(YEAR_MONTH));
        for (int i = 0; i < addNum; i++) {
            times.add(dateTime.minusMonths(i + 1).toString(YEAR_MONTH, Locale.CHINESE));
        }
        Collections.reverse(times);//倒序
        return times;
    }

}
