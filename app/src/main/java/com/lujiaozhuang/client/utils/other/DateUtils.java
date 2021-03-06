/*
 * @(#)DateUtils.java    Created on 2004-10-20
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Id: DateUtils.java 34780 2013-02-17 10:43:59Z xuan $
 */
package com.lujiaozhuang.client.utils.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理日期时间的工具类
 *
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-3-25 上午9:22:45 $
 */
@SuppressLint("SimpleDateFormat")
public abstract class DateUtils {

    public static final String TIME_HH = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_HHH = "yyyy-MM-dd-HH-mm-ss";
    public static final String TIME = "yyyy-MM-dd";
    public static final String TIME_MM = "MM-dd";
    public static final String TIME_YM = "yyyy-MM";


    private static final int[] DAY_OF_MONTH = new int[]{31, 28, 31, 30, 31,
            30, 31, 31, 30, 31, 30, 31};

    /**
     * 按照指定格式把时间转换成字符串，格式的写法类似yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param calender 时间
     * @param pattern  格式
     * @return 时间字符串
     */
    @SuppressLint("SimpleDateFormat")

    public static String calenderToString(Calendar calender, String pattern) {
        if (calender == null) {
            return null;
        }
        return getFormatTime(calender, pattern);
    }

    /**
     * 输入日期如（2014-06-14-16-09-00）返回（星期数）
     *
     * @param time
     * @return
     */
    public static String weekOne(String time) {
        Date date = null;
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "日";
        } else if (mydate == 2) {
            week = "一";
        } else if (mydate == 3) {
            week = "二";
        } else if (mydate == 4) {
            week = "三";
        } else if (mydate == 5) {
            week = "四";
        } else if (mydate == 6) {
            week = "五";
        } else if (mydate == 7) {
            week = "六";
        }
        return week;
    }

    /**
     * 输入日期如（2014-06-14）返回（星期数）
     *
     * @param time
     * @return
     */
    public static String weekTwo(String time) {
        Date date = null;
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "日";
        } else if (mydate == 2) {
            week = "一";
        } else if (mydate == 3) {
            week = "二";
        } else if (mydate == 4) {
            week = "三";
        } else if (mydate == 5) {
            week = "四";
        } else if (mydate == 6) {
            week = "五";
        } else if (mydate == 7) {
            week = "六";
        }
        return week;
    }

    /**
     * @return
     */
    public static String getCurrentTime() {
        return getFormatTime(Calendar.getInstance(), "yyyy-MM-dd");
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static long getDateLong() {
        return new Date().getTime();
    }


    /**
     * 取得当前时间的字符串表示，格式为2006-01-10 20:56:30.756
     *
     * @return 当前时间的字符串表示
     */
    public static String getCurrentTimeYD() {
        return getFormatTime(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * **
     * 创建时间: 2016/10/12 11:08
     * <p>
     * 方法功能：获取指定格式的当前时间
     */

    public static String getCurrentTime(String format) {
        return getFormatTime(Calendar.getInstance(), format);
    }

    /**
     * 获取下个月月份 格式为   yyyy-MM  (2017-11)
     * @return
     */
    public static String getNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.TIME_YM);
        return sdf.format(calendar.getTime());
    }

    /**
     * 取得今天的最后一个时刻
     *
     * @return 今天的最后一个时刻
     */
    public static Calendar currentEndTime() {
        return getEndTime(Calendar.getInstance());
    }

    /**
     * 获取某天的结束时间, e.g. 2005-10-01 23:59:59.999
     *
     * @param calender 日期对象
     * @return 该天的结束时间
     */
    public static Calendar getEndTime(Calendar calender) {
        if (calender == null) {
            calender = Calendar.getInstance();
        }
        calender.set(Calendar.HOUR_OF_DAY, 23);
        calender.set(Calendar.MINUTE, 59);
        calender.set(Calendar.SECOND, 59);
        calender.set(Calendar.MILLISECOND, 999);

        return calender;
    }

    /**
     * 取得今天的第一个时刻
     *
     * @return 今天的第一个时刻
     */
    public static Calendar currentStartTime() {
        return getStartTime(Calendar.getInstance());
    }

    /**
     * 获取某天的起始时间, e.g. 2005-10-01 00:00:00.000
     *
     * @param calender 日期对象
     * @return 该天的起始时间
     */
    public static Calendar getStartTime(Calendar calender) {
        if (calender == null) {
            calender = Calendar.getInstance();
        }
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);
        return calender;
    }

    public static String getFormatTime(Calendar calendar, String format) {
        if (calendar == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    public static Calendar getParseTime(String calendar, String format) {
        if (calendar == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(calendar));
            return c;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 取得指定天数后的时间
     *
     * @param calendar  基准时间
     * @param dayAmount 指定天数，允许为负数
     * @return 指定天数后的时间
     */
    public static Calendar addDay(Calendar calendar, int dayAmount) {
        if (calendar == null) {
            return null;
        }
        calendar.add(Calendar.DAY_OF_YEAR, dayAmount);
        return calendar;
    }

    /**
     * 取得指定小时数后的时间
     *
     * @param calendar   基准时间
     * @param hourAmount 指定小时数，允许为负数
     * @return 指定小时数后的时间
     */
    public static Calendar addHour(Calendar calendar, int hourAmount) {
        if (calendar == null) {
            return null;
        }
        calendar.add(Calendar.HOUR, hourAmount);
        return calendar;
    }

    /**
     * 取得指定分钟数后的时间
     *
     * @param calendar     基准时间
     * @param minuteAmount 指定分钟数，允许为负数
     * @return 指定分钟数后的时间
     */
    public static Calendar addMinute(Calendar calendar, int minuteAmount) {
        if (calendar == null) {
            return null;
        }

        calendar.add(Calendar.MINUTE, minuteAmount);
        return calendar;
    }

    /**
     * 比较两日期对象中的小时和分钟部分的大小.
     *
     * @param calendar        日期对象1, 如果为 <code>null</code> 会以当前时间的日期对象代替
     * @param anotherCalender 日期对象2, 如果为 <code>null</code> 会以当前时间的日期对象代替
     * @return 如果日期对象1大于日期对象2, 则返回大于0的数; 反之返回小于0的数; 如果两日期对象相等, 则返回0.
     */
    public static int compareHourAndMinute(Calendar calendar, Calendar anotherCalender) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        if (anotherCalender == null) {
            anotherCalender = Calendar.getInstance();
        }

        int hourOfDay1 = calendar.get(Calendar.HOUR_OF_DAY);
        int minute1 = calendar.get(Calendar.MINUTE);

        int hourOfDay2 = anotherCalender.get(Calendar.HOUR_OF_DAY);
        int minute2 = anotherCalender.get(Calendar.MINUTE);

        if (hourOfDay1 > hourOfDay2) {
            return 1;
        } else if (hourOfDay1 == hourOfDay2) {
            // 小时相等就比较分钟
            return minute1 > minute2 ? 1 : (minute1 == minute2 ? 0 : -1);
        } else {
            return -1;
        }
    }

    /**
     * 比较两日期对象的大小, 忽略秒, 只精确到分钟.
     *
     * @param calendar        日期对象1, 如果为 <code>null</code> 会以当前时间的日期对象代替
     * @param anotherCalender 日期对象2, 如果为 <code>null</code> 会以当前时间的日期对象代替
     * @return 如果日期对象1大于日期对象2, 则返回大于0的数; 反之返回小于0的数; 如果两日期对象相等, 则返回0.
     */
    public static int compareIgnoreSecond(Calendar calendar, Calendar anotherCalender) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        if (anotherCalender == null) {
            anotherCalender = Calendar.getInstance();
        }

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        anotherCalender.set(Calendar.SECOND, 0);
        anotherCalender.set(Calendar.MILLISECOND, 0);
        return calendar.compareTo(anotherCalender);
    }


    /**
     * 根据某星期几的英文名称来获取该星期几的中文数. <br>
     * e.g. <li>monday -> 一</li> <li>sunday -> 日</li>
     *
     * @param englishWeekName 星期的英文名称
     * @return 星期的中文数
     */
    public static String getChineseWeekNumber(String englishWeekName) {
        if ("monday".equalsIgnoreCase(englishWeekName)) {
            return "一";
        }

        if ("tuesday".equalsIgnoreCase(englishWeekName)) {
            return "二";
        }

        if ("wednesday".equalsIgnoreCase(englishWeekName)) {
            return "三";
        }

        if ("thursday".equalsIgnoreCase(englishWeekName)) {
            return "四";
        }

        if ("friday".equalsIgnoreCase(englishWeekName)) {
            return "五";
        }

        if ("saturday".equalsIgnoreCase(englishWeekName)) {
            return "六";
        }

        if ("sunday".equalsIgnoreCase(englishWeekName)) {
            return "日";
        }

        return null;
    }

    /**
     * 根据指定的年, 月 等参数获取日期对象.
     *
     * @param year
     * @param month
     * @return
     */
    public static Calendar getTime(int year, int month) {
        return getTime(year, month, 0, 0, 0);
    }


    /**
     * 根据指定的年, 月, 日等参数获取日期对象.
     *
     * @param year  年
     * @param month 月
     * @param date  日
     * @return 对应的日期对象
     */
    public static Calendar getTime(int year, int month, int date) {
        return getTime(year, month, date, 0, 0);
    }

    /**
     * 根据指定的年, 月, 日, 时, 分等参数获取日期对象.
     *
     * @param year      年
     * @param month     月
     * @param date      日
     * @param hourOfDay 时(24小时制)
     * @param minute    分
     * @return 对应的日期对象
     */
    public static Calendar getTime(int year, int month, int date, int hourOfDay,
                                   int minute) {
        return getTime(year, month, date, hourOfDay, minute, 0);
    }

    /**
     * 根据指定的年, 月, 日, 时, 分, 秒等参数获取日期对象.
     *
     * @param year      年
     * @param month     月
     * @param date      日
     * @param hourOfDay 时(24小时制)
     * @param minute    分
     * @param second    秒
     * @return 对应的日期对象
     */
    public static Calendar getTime(int year, int month, int date, int hourOfDay,
                                   int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, date, hourOfDay, minute, second);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    /**
     * 取得某个日期是星期几，星期日是1，依此类推
     *
     * @param calender 日期
     * @return 星期几
     */
    public static int getDayOfWeek(Calendar calender) {
        return calender.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 根据日期获取周几，星期日是1，依此类推
     *
     * @param calender 日期
     * @param day      第几天
     * @return 星期几
     */
    public static int getDayOfWeek(Calendar calender, int day) {
        calender.set(Calendar.DAY_OF_MONTH, day);
        return calender.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 返回中文的周几
     *
     * @param calender 时间对象
     * @param day      第几
     * @return 返回的时间
     */
    public static String getDayOfWeekStr(Calendar calender, int day) {
        calender.set(Calendar.DAY_OF_MONTH, day);
        int week = calender.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            return "日";
        } else if (week == 2) {
            return "一";
        } else if (week == 3) {
            return "二";
        } else if (week == 4) {
            return "三";
        } else if (week == 5) {
            return "四";
        } else if (week == 6) {
            return "五";
        } else if (week == 7) {
            return "六";
        }
        return "未知";
    }

    /**
     * 返回数字的周几
     *
     * @param calender 时间对象
     * @param day      第几
     * @return 返回的时间
     */
    public static int getDayOfWeekInt(Calendar calender, int day) {
        calender.set(Calendar.DAY_OF_MONTH, day);
        int week = calender.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            return 7;
        } else if (week == 2) {
            return 1;
        } else if (week == 3) {
            return 2;
        } else if (week == 4) {
            return 3;
        } else if (week == 5) {
            return 4;
        } else if (week == 6) {
            return 5;
        } else if (week == 7) {
            return 6;
        }
        return -1;
    }


    /**
     * 获取当月最大几号
     *
     * @param calender 日期
     * @return 当月的最大号数
     */
    public static int getMaxDay(Calendar calender) {
        calender.set(Calendar.DATE, 1);
        calender.roll(Calendar.DATE, -1);
        int day = calender.get(Calendar.DATE);
        calender.roll(Calendar.DATE, 1);
        return day;
    }


    /**
     * 取得一个月最多的天数
     *
     * @param year  年份
     * @param month 月份，0表示1月，依此类推
     * @return 最多的天数
     */
    public static int getMaxDayOfMonth(int year, int month) {
        if (month == 1 && isLeapYear(year)) {
            return 29;
        }
        return DAY_OF_MONTH[month];
    }

    /**
     * 得到指定日期的下一天
     *
     * @param calender 日期对象
     * @return 同一时间的下一天的日期对象
     */
    public static Calendar getNextDay(Calendar calender) {
        return addDay(calender, 1);
    }


    /**
     * 根据日期对象来获取日期中的时间(HH:mm:ss).
     *
     * @param calender 日期对象
     * @return 时间字符串, 格式为: HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeHHSS(Calendar calender) {
        return getFormatTime(calender, "HH:mm:ss");
    }


    /**
     * 根据日期对象来获取日期中的时间(HH:mm).
     *
     * @param calender 日期对象
     * @return 时间字符串, 格式为: HH:mm
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeIgnoreSecond(Calendar calender) {
        return getFormatTime(calender, "HH:mm");
    }

    /**
     * 判断是否是闰年
     *
     * @param year 年份
     * @return 是true，否则false
     */
    public static boolean isLeapYear(int year) {
        Calendar calendar = Calendar.getInstance();
        return ((GregorianCalendar) calendar).isLeapYear(year);
    }

    /**
     * 把字符串转换成日期，格式为2006-01-10 20:56:30
     *
     * @param str 字符串
     * @return 日期
     */
    public static Calendar stringToTime(String str) {
        return stringToTime(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照指定的格式把字符串转换成时间，格式的写法类似yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param str     字符串
     * @param pattern 格式
     * @return 时间
     */
    public static Calendar stringToTime(String str, String pattern) {
        return getParseTime(str, pattern);
    }


    /**
     * 取得一年中的第几周。
     *
     * @param calender
     * @return
     */
    public static int getWeekOfYear(Calendar calender) {
        return calender.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     * 获取星期
     *
     * @param calender
     * @return
     */
    public static String getWeekOfDate(Calendar calender) {
        if (calender == null) return null;
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = calender.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    /**
     * 把字符串转换成日期，格式为2006-01-10   年月日
     *
     * @param str 字符串
     * @return 返回日期String
     */
    public static String stringToString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return getFormatTime(stringToTime(str, "yyyy-MM-dd"), "yyyy-MM-dd");
    }

    /**
     * 把字符串转换成日期,
     *
     * @param str 字符串, 必须是yyyy-MM-dd HH:mm:ss
     * @return 返回日期String
     */
    public static String string2String(String str, String pattern) {
        return getFormatTime(stringToTime(str, pattern), pattern);
    }


    /**
     * 根据时间戳，转换为指定格式的时间
     *
     * @param date   这个字符串要包含时间戳
     * @param format 时间格式
     * @return 对应的时间
     */
    public static String dateToString(String date, String format) {
        if (StringUtils.isEmpty(date)) return "";
        long time = dateToLong(date);
        if (time == 0) {
            return "";
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return getFormatTime(c, format);
    }

    /**
     * 根据时间戳，转换为指定格式的时间
     *
     * @param date   时间戳
     * @param format 时间格式
     * @return 对应的时间
     */
    public static String dateToString(long date, String format) {

        return dateToString(String.valueOf(date), format);
    }


    /**
     * \/Date(1461686400000)\/ 转换成指定格式的String "(?<=p[(])\d{4}(?=,\d,\d[)];)" "p(1003,7,9);p(1048,1,2);p(1009,2,2);"
     */
    public static Long dateToLong(String date) {
        if (StringUtils.isEmpty(date)) return 0l;
        Pattern p = Pattern.compile("\\d{10,}");
        Matcher matcher = p.matcher(date);
        long time = 0;
        while (matcher.find()) { //注意这里，是while不是if
            time = Long.valueOf(matcher.group());
        }
        if (time == 0) {
            return 0l;
        }
        return time;
    }


    public static int getDay(String date) {
        return stringToTime(date).get(Calendar.DAY_OF_MONTH);
    }


    public static int getMonth(String date) {
        return stringToTime(date).get(Calendar.MONTH) + 1;
    }


    /**
     * 传过来一个还long的字符串 根据时间的的长短返回对应的时间 1，不到1分钟返回刚刚 2，超过1分钟返回**分钟 3，不到1天返回**小时
     * 4，超过1天返回long 的日期
     *
     * @param text
     * @return
     */
    public static String getTime(String text) {
        try {
            if (StringUtils.isBlank(text)) {
                return "";
            }

            // 1.把传过来的Date 中的数字取出来。2.然后设置时间格式。3.把一个long 数值转换成日字符串
            long time_long = dateToLong(text);

            Calendar currData = Calendar.getInstance();
            Calendar mubiaoData = Calendar.getInstance();
            mubiaoData.setTimeInMillis(time_long);

            if (currData.get(Calendar.DAY_OF_YEAR) > mubiaoData.get(Calendar.DAY_OF_YEAR) || currData.get(Calendar.YEAR) > mubiaoData.get(Calendar.YEAR)) {
                //今天之前
                return calenderToString(mubiaoData, "yyyy-MM-dd");
            } else {
                //今天
                int caizhi = (int) ((currData.getTimeInMillis() - mubiaoData.getTimeInMillis()) / 1000.0);//秒
                if (caizhi <= 60) { //一分钟之内
                    return "刚刚";
                } else if (caizhi <= 60 * 60) { //一小时之内
                    return (int) (caizhi / 60.0) + "分钟前";
                } else {
                    return (int) (caizhi / 60.0 / 60.0) + "小时前";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;

    }

    public static String getTime(long time) {
        return getTime(String.valueOf(time));
    }


    public static String getLocalTime(Context context, String time) {
        // 取出年月日来，比较字符串即可
        String str_curTime = DateFormat.format("yyyy-MM-dd", new Date())
                .toString();
        int result = str_curTime
                .compareTo(time.substring(0, time.indexOf(" ")));
        if (result > 0) {
            return "昨天"
                    + time.substring(time.indexOf(" "), time.lastIndexOf(":"));
        } else if (result == 0) {
            return "今天"
                    + time.substring(time.indexOf(" "), time.lastIndexOf(":"));
        } else {
            return time;
        }
    }

    /**
     * string类型转换为date类型 strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd
     * HH:mm:ss//yyyy年MM月dd日HH时mm分ss秒， strTime的时间格式必须要与formatType的时间格式相同
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date converStringToDate(String strTime, String formatType) {
        try {
            Date date = null;
            SimpleDateFormat formatter = new SimpleDateFormat(formatType);
            date = formatter.parse(strTime);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }


    /**
     * 把字符的时间转换成一Long值
     *
     * @param dateStr 时间
     * @param format  时间格式
     * @return
     */
    public static Long dateToLong(String dateStr, String format) {
        long time = 0;
        try {
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

}
