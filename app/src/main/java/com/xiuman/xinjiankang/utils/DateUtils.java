package com.xiuman.xinjiankang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 描述：日期格式化类
 * Created by hxy on 2015/8/20.
 */
public class DateUtils {
    public static String dateFormat(double dou){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = sdf.format(dou);
        return  format;
    }
    public static String dateFormat2(double dou){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(dou);
        return  format;
    }
    public static String dateFormat3(double dou){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(dou);
        return  format;
    }
    public static long StringToLong(String str){
        long millionSeconds = 0;//毫秒
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            millionSeconds = sdf.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millionSeconds;
    }
}
