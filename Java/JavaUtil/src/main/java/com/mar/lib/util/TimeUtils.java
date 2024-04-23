package com.mar.lib.util;

import java.util.Date;

public class TimeUtils {
    public static String getHumanReadableTime(Date time){
        return getHumanReadableTime(time.getTime());
    }

    public static String getHumanReadableTime(long time){
        long interval = System.currentTimeMillis() - time;
        long sec = 1000;
        long min = 60*sec;
        long hour = 60*min;
        long day = 24*hour;
        long month = 30*day;
        if(interval<0){
            return "--";
        }else if(interval<5*min){
            return "刚刚";
        }else if(interval<hour){
            return (interval/min)+"分钟前";
        }else if(interval<day){
            return (interval/hour)+"小时前";
        }else if(interval<month){
            return (interval/day)+"天前";
        }else if(interval<12*month){
            return (interval/month)+"个月前";
        }else{
            return "1年前";
        }
    }
}
