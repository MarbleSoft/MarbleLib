package com.mar.lib.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间处理相关工具类
 * 例如：获取各种类型时间，格式化处理时间等
 * Jimmy
 */
public class DateUtils {
    public static final String YMD = "yyyy-MM-dd";
    public static final String YMD_DOT = "yyyy.MM.dd";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    @Retention(RetentionPolicy.SOURCE)
    public @interface PATTERN {
    }

    /**
     * 格式化当前时间
     *
     * @param pattern PATTERN
     * @return 对应模式的字符串
     * @author Jimmy
     */
    public static String getCurrentDate(@PATTERN String pattern) {
        if (StrUtils.isEmpty(pattern)) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 获取秒钟转换成的分钟字符串 几分几秒
     *
     * @param seconds_str
     * @return
     */
    public static String getMinuteStr(String seconds_str) {
        if (StrUtils.isEmpty(seconds_str)) {
            return "";
        }
        try {
            int seconds = Integer.valueOf(seconds_str);
            return getMinuteStr(seconds);
        } catch (NumberFormatException e) {
            return "";
        }
    }

    /**
     * 获取秒钟转换成的分钟字符串 几分几秒
     *
     * @param seconds s
     * @return
     */
    public static String getMinuteStr(int seconds) {
        try {
            if (seconds / 60 > 0) {
                if (seconds % 60 == 0) {
                    return seconds / 60 + "分钟";
                }
                return seconds / 60 + "分钟" + seconds % 60 + "秒";
            } else {
                return seconds + "秒";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取时间间隔
     */
    public static String getTimeInterval(String lastTime) {
        try {
            if (StrUtils.isEmpty(lastTime)) {
                return "";
            }
            long timeInterval = System.currentTimeMillis() / 1000 - Long.valueOf(lastTime);
            if (timeInterval / 3600 / 24 > 0) {
                return timeInterval / 3600 / 24 + "天前";
            } else if (timeInterval / 3600 > 0) {
                return timeInterval / 3600 + "小时前";
            } else if (timeInterval / 60 > 0) {
                return timeInterval / 60 + "分钟前";
            } else if (timeInterval / 60 == 0) {
                return "刚刚";
            } else {
                SimpleDateFormat sp = new SimpleDateFormat("MM-dd HH:mm");
                return sp.format(Long.valueOf(lastTime) * 1000);
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将传入的秒格式化为 HH:mm:ss的格式
     * @param millisUntilFinished:毫秒
     * @return
     */
    public static String formatTimeString(long millisUntilFinished) {
        return formatTimeString((int)millisUntilFinished/1000);
    }

    /**
     * 将传入的秒格式化为 HH:mm:ss的格式
     * @param seconds：秒
     * @return
     */
    public static String formatTimeString(int seconds) {
        long first = 0, twice = 0, third = 0;
        long mtmp = 0, mtmp2 = 0;
        final int SECONDS = 60;    //秒数
        final int MINUTES = 60 * 60;    //小时

        first = seconds;
        if (first < SECONDS) {    //小于一分钟 只显示秒
            return "00:00:" + (first < 10 ? "0" + first : first);
        } else if (first < MINUTES) {    //大于或等于一分钟，但小于一小时，显示分钟
            twice = first % 60;    //将秒转为分钟取余，余数为秒
            mtmp = first / 60;    //将秒数转为分钟
            if (twice == 0) {
                return "00:" + (mtmp < 10 ? "0" + mtmp : mtmp) + ":00";    //只显示分钟
            } else {
                return "00:" + (mtmp < 10 ? "0" + mtmp : mtmp) + ":" + (twice < 10 ? "0" + twice : twice);    //显示分钟和秒
            }
        } else {
            twice = first % 3600;    //twice为余数 如果为0则小时为整数
            mtmp = first / 3600;
            if (twice == 0) {
                //只剩下小时
                return (mtmp < 10 ? "0" + mtmp : mtmp) + ":00:00";
            } else {
                if (twice < SECONDS) {    //twice小于60 为秒
                    return (mtmp < 10 ? "0" + mtmp : mtmp) + ":00:" + (twice < 10 ? "0" + twice : twice);    //显示小时和秒
                } else {
                    third = twice % 60;    //third为0则剩下分钟 否则还有秒
                    mtmp2 = twice / 60;
                    if (third == 0) {
                        return (mtmp < 10 ? "0" + mtmp : mtmp) + ":" + (mtmp2 < 10 ? "0" + mtmp2 : mtmp2) + ":00";
                    } else {
                        return (mtmp < 10 ? "0" + mtmp : mtmp) + ":" + (mtmp2 < 10 ? "0" + mtmp2 : mtmp2) + ":" + (third < 10 ? "0" + third : third) ;    //还有秒
                    }
                }
            }
        }
    }


    /**
     * 获取24点的时间戳
     *
     * @return
     */
    public static long getTimesNight() {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 24);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取某年某月某日某时某分某秒对应的时间戳
     * @param year:年
     * @param month：月,从0开始计算，0代表1月份
     * @param date：日
     * @param hourOfDay：时
     * @param minute：分
     * @param second：秒
     * @return:时间戳
     */
    public static long getSomeTimeMillis(int year, int month, int date, int hourOfDay, int minute, int second, int millisecond) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, date, hourOfDay, minute, second);
            cal.set(Calendar.MILLISECOND, millisecond);
            return cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断当前是否在时间段内
     *
     * @param startTime:起始时间，单位秒
     * @param endTime:截止时间，单位秒
     * @return：true代表当前时间在起始时间和截止时间之间，false反之
     */
    public static boolean isInTime(long startTime, long endTime) {
        long currentTime = System.currentTimeMillis() / 1000;
        return currentTime > startTime && currentTime < endTime;
    }

    /**
     * 获取多少天之后的时间戳
     *
     * @return
     */
    public static long getDaysLater(int day) {
        return System.currentTimeMillis() + day * 24 * 3600 * 1000;
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    private static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }


    /**
     * 获取当年的最后一天
     *
     * @return
     */
    private static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }


    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    private static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 通过时间戳获取当前时间
     * System.currentTimeMillis()->刚刚
     */
    public static String getVideoPublishStr(long timeStamp) {
        long thisYearFirstTime = getCurrYearFirst().getTime();
        long thisYearLastTime = getCurrYearLast().getTime();

        long timeToNow = (System.currentTimeMillis() - timeStamp) / 1000;
        long oneDay = 24 * 3600;
        long oneHour = 3600;
        if (timeToNow >= 0 && timeToNow <= oneHour) {
            return "刚刚";
        } else if (timeToNow > 0 && timeToNow < oneDay) {
            return timeToNow / oneHour + "小时前";
        } else if (timeStamp >= thisYearFirstTime && timeStamp <= thisYearLastTime) {
            SimpleDateFormat sp = new SimpleDateFormat("MM-dd");
            return sp.format(new Date(timeStamp));
        } else {
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
            return sp.format(new Date(timeStamp));
        }
    }

    /**
     * 获取视频时长：分钟
     */
    public static String getVideoDurationMin(String seconds) {
        int s = NumberUtils.parseInt(seconds);

        if (s <= 0) {
            return "00:00";
        }
        int second = s % 60;
        int min = s / 60;
        return unitFormat(min) + ":" + unitFormat(second);
    }


    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * @param secondSum 总秒数
     * @return XX:XX
     */
    public static String getTimeLengthStr(int secondSum) {

        String time;

        if (secondSum < 60) {

            time = "00:" + NumberUtils.formatNum(secondSum);

        } else {
            int m = secondSum / 60;
            int s = secondSum - 60 * m;
            time = NumberUtils.formatNum(m) + ":" + NumberUtils.formatNum(s);
        }
        return time;
    }

    /**
     * 时间戳转日历时间，精确到秒.例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param timeStamp:单位秒
     * @return
     */
    public static String formatDate(String timeStamp) {
        if (StrUtils.isEmpty(timeStamp)) {
            return "";
        }
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        int i = NumberUtils.parseInt(timeStamp);
        return sdr.format(new Date(i * 1000L));

    }

    /**
     * 根据传入的format格式化时间戳
     *
     * @param timeStamp
     * @return
     */
    public static String formatDate(String timeStamp, String format) {
        if (StrUtils.isEmpty(timeStamp) || StrUtils.isEmpty(format)) {
            return "";
        }
        SimpleDateFormat sdr = new SimpleDateFormat(format);
        long lcc = NumberUtils.parseLong(timeStamp);
        return sdr.format(new Date(lcc * 1000L));
    }

    /**
     * 格式化，精确到毫秒
     * @return
     */
    public static String formatDateMiliSec(String timeStamp, String format) {
        if (StrUtils.isEmpty(timeStamp) || StrUtils.isEmpty(format)) {
            return "";
        }
        SimpleDateFormat sdr = new SimpleDateFormat(format);
        long lcc = NumberUtils.parseLong(timeStamp);
        return sdr.format(new Date(lcc));
    }

    /**
     * 毫秒转化时分秒
     * 1000*60L -> 00:01:00
     */
    public static String formatDayRankTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if (hour >= 10) {
            sb.append(hour + ":");
        } else {
            sb.append("0" + hour + ":");
        }
        if (minute >= 10) {
            sb.append(minute + ":");
        } else {
            sb.append("0" + minute + ":");
        }
        if (second >= 10) {
            sb.append(second);
        } else {
            sb.append("0" + second);
        }
        return sb.toString();
    }


    /**
     * 毫秒转换为天,距离1970年的天数
     * @param ms：单位毫秒,时间戳
     */
    public static String formatDayTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;

        StringBuffer sb = new StringBuffer();
        sb.append(day);
        return sb.toString();
    }


    /**
     * 毫秒转化天时分秒,距离1970年的天数
     * @param ms:毫秒
     */
    public static String formatWeekMonthRankTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        sb.append(day + "天");
        if (hour >= 10) {
            sb.append(hour + ":");
        } else {
            sb.append("0" + hour + ":");
        }
        if (minute >= 10) {
            sb.append(minute + ":");
        } else {
            sb.append("0" + minute + ":");
        }
        if (second >= 10) {
            sb.append(second);
        } else {
            sb.append("0" + second);
        }
        return sb.toString();
    }


    /**
     * 时间格式化到天
     *
     * @param timeMillis:该参数是从1970年1月1日起的毫秒数。
     * @return
     */
    public static String formatDate2Day(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat(YMD_DOT);
        Date date = new Date(timeMillis);
        return sdf.format(date);
    }

    /**
     * 判断两个时间是不是同一天
     *
     * @param dateMillis:时间戳，毫秒
     * @param sameDateMIllis：时间戳，毫秒
     * @return
     */
    public static boolean isSameDay(long dateMillis, long sameDateMIllis) {
        Date date = new Date(dateMillis);
        Date sameDate = new Date(sameDateMIllis);

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(sameDate);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)
                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)
                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE)) {
            return true;
        }
        return false;
    }


    /**
     * 毫秒跟小时取余，获取余下的分钟数,eg: 61 -> 01
     * @param mss 秒
     * @return
     */
    public static String formatMinExclusiveHour(long mss) {
        long min = (mss % (60 * 60)) / (60);
        if (min > 9) {
            return String.valueOf(min);
        } else {
            return "0" + String.valueOf(min);
        }
    }

    /**
     * 余多少秒。65 -> 05
     * @param mss 秒
     * @return
     */
    public static String formatSec(long mss) {
        long sec = mss % (60);
        if (sec > 9) {
            return String.valueOf(sec);
        } else {
            return "0" + String.valueOf(sec);
        }
    }

    /**
     * 秒转天。例如：60*60*24 -> 01
     * @param mss 秒
     * @return
     */
    public static String formatDay(long mss) {
        long day = mss / (60 * 60 * 24);
        if (day > 9) {
            return String.valueOf(day);
        } else {
            return "0" + String.valueOf(day);
        }
    }

    /**
     * 秒转小时。例如：60*60 -> 01
     * @param mss 秒
     * @return
     */
    public static String formatHour(long mss) {
        long hour = (mss % (60 * 60 * 24)) / (60 * 60);
        if (hour > 9) {
            return String.valueOf(hour);
        } else {
            return "0" + String.valueOf(hour);
        }
    }

    /**
     * 描述：计算两个日期相隔的天数.
     *
     * @param date1 第一个时间的毫秒表示开始时间
     * @param date2 第二个时间的毫秒表示结束时间
     * @return long 相隔的天数
     */
    public static long getOffectDay(long date1, long date2) {
        long degreeDays = 0;
        SimpleDateFormat df = new SimpleDateFormat(YMD);
        String begin = df.format(new Date(date1));
        String end = df.format(new Date(date2));
        Date beginDate;
        Date endDate;
        try {
            beginDate = df.parse(begin);
            endDate = df.parse(end);
            degreeDays = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return degreeDays;
    }

    /**
     * 时间格式转化
     * 将毫秒值 转换成 年月日 +（昨天 明天 今天）
     * @param timeStamp 毫秒值
     * @return
     */
    public static String formatSpecailDate(long timeStamp) {
        //*年*月*日
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        String dateStr = sdr.format(new Date(timeStamp));
        //今天，明天，昨天
        if(isSameDay(System.currentTimeMillis(), timeStamp)) {
            dateStr += " 今天";
        } else if(isSameDay(System.currentTimeMillis() + 24 * 60 * 60 * 1000, timeStamp)) {
            dateStr += " 明天";
        } else if(isSameDay(System.currentTimeMillis() - 24 * 60 * 60 * 1000, timeStamp)) {
            dateStr += " 昨天";
        }
        return dateStr;
    }

    /**
     * 格式化时间戳
     * 秒格式化。例如System.currentTimeMillis()/1000 -> 2017-12-22 20:54
     * @param stamp：秒
     * @return
     */
    public static String timeTool(long stamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String start = format.format(stamp * 1000);
        return start;
    }

    /**
     * 格式化时间戳
     * 秒格式化,精确到秒。例如System.currentTimeMillis()/1000 -> 2017-12-22 20:57:54
     * @param stamp:秒
     * @return
     */
    public static String getAllTimeTool(long stamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = format.format(stamp * 1000);
        return start;
    }

    /**
     * 当天零点时间戳
     *
     * @return:毫秒
     */
    public static long getTodayZero() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        return (date.getTime() - (date.getTime() % l) - TimeZone.getDefault().getRawOffset());
    }

    /**
     * 秒数转成时分秒的格式
     *
     * @param time
     * @return
     */
    public static String secToTime(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Long.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

}
