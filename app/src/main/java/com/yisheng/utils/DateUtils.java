package com.yisheng.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* 
 * 时间格式化
 */
public class DateUtils {

    private static SimpleDateFormat sf = null;
    private static SimpleDateFormat sdf;

    public static boolean DateCompare(String s1, String s2) {
        L.e("开始时间s1===》"+s1+"结束时间s2===》"+s2);
        // 设定时间的模板
        Date start=null;
        Date end=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        // 得到指定模范的时间

        try {
            start = sdf.parse(s1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            end = sdf.parse(s2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        L.e("开始时间d1===》"+start.getTime()+"结束时间d2===》"+end.getTime());
        // 比较
        if (start.getTime() <= end.getTime()) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean DateMonthCompare(String s1, String s2) {
        L.e("开始时间s1===》"+s1+"结束时间s2===》"+s2);
        // 设定时间的模板
        Date start=null;
        Date end=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        // 得到指定模范的时间

        try {
            start = sdf.parse(s1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            end = sdf.parse(s2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        L.e("开始时间d1===》"+start.getTime()+"结束时间d2===》"+end.getTime());
        // 比较
        if ((end.getTime()-start.getTime())/(1000*60*60*24)<=31) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time);
        return t;
    }

    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time, String dateFormat) {
        Date d = new Date(time);//ms
        sf = new SimpleDateFormat(dateFormat);
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getMyDateToString(long time) {
        Date d = new Date(time);//ms
        sf = new SimpleDateFormat("MM月dd日 HH:mm");
        return sf.format(d);
    }

    public static String getDateToString2(long time) {
        Date d = new Date(time);//ms
        sf = new SimpleDateFormat("MM月dd日");
        return sf.format(d);
    }

    /*将字符串转为时间戳 精确到秒*/
    public static long getStringToDate(String time) {
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
    /*将字符串转为时间戳 精确到日*/
    public static long getStringDayToDate(String time) {
        sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}
