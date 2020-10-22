package com.kingkiller.util;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static final String FULL_PATTERN = "yyyyMMddHHmmss";

    /**
     * 自定义日期转换方法
     * @param date 日期类
     * @param pattern 转换格式
     * @return 时间
     */
    public static String formateString(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = sdf.format(date);
        return str;
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static Date getCurrentDate() {
        return DateTime.now().toDate();
    }

    /**
     * 获取当前时间 格式： yyyyMMddHHmmss
     *
     * @return 字符日期 格式：yyyyMMddHHmmss
     */
    public static String getCurrent() {
        return getCurrent(FULL_PATTERN);
    }

    /**
     * 获取当前时间 格式： 自定义
     *
     * @param pattern 时间格式
     * @return 自定义格式的当前时间
     */
    public static String getCurrent(String pattern) {
        return DateTime.now().toString(pattern);
    }

}
