package fansirsqi.xposed.sesame.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具类。 提供了一系列方法来处理时间相关的操作，包括时间范围检查、时间比较、日期格式化等。
 */
public class TimeUtil {
    private static final String TAG = "TimeUtil";

    /**
     * 获取当前时间戳
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串
     */
    public static String getCurrentTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(getCurrentTimeMillis());
    }

    /**
     * 获取当前日期字符串
     */
    public static String getCurrentDateString() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(getCurrentTimeMillis());
    }

    /**
     * 根据时间戳获取Calendar对象
     */
    public static Calendar getCalendarByTimeMillis(Long timeMillis) {
        if (timeMillis == null) {
            timeMillis = getCurrentTimeMillis();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return calendar;
    }

    /**
     * 根据时间字符串获取今天的Calendar对象
     */
    public static Calendar getTodayCalendarByTimeStr(String timeStr) {
        return getCalendarByTimeStr(null, timeStr);
    }

    /**
     * 根据时间字符串获取Calendar对象
     */
    public static Calendar getCalendarByTimeStr(Long timeMillis, String timeStr) {
        try {
            Calendar calendar = getCalendarByTimeMillis(timeMillis);
            if (timeStr != null && !timeStr.isEmpty()) {
                String[] timeParts = timeStr.split(":");
                if (timeParts.length >= 2) {
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                }
            }
            return calendar;
        } catch (Exception e) {
            Log.printStackTrace(TAG, e);
            return null;
        }
    }

    /**
     * 比较时间戳与时间字符串
     */
    public static Integer compareTimeStr(Long timeMillis, String compareTimeStr) {
        try {
            Calendar timeCalendar = getCalendarByTimeMillis(timeMillis);
            Calendar compareCalendar = getTodayCalendarByTimeStr(compareTimeStr);
            if (compareCalendar != null) {
                return timeCalendar.compareTo(compareCalendar);
            }
        } catch (Exception e) {
            Log.printStackTrace(TAG, e);
        }
        return null;
    }

    /**
     * 检查当前时间是否在指定时间之后
     */
    public static boolean isNowAfterTimeStr(String afterTimeStr) {
        return isAfterTimeStr(getCurrentTimeMillis(), afterTimeStr);
    }

    /**
     * 检查时间戳是否在指定时间之前
     */
    public static boolean isBeforeTimeStr(Long timeMillis, String beforeTimeStr) {
        Integer compared = compareTimeStr(timeMillis, beforeTimeStr);
        return compared != null && compared < 0;
    }

    /**
     * 检查时间戳是否在指定时间之后
     */
    public static boolean isAfterTimeStr(Long timeMillis, String afterTimeStr) {
        Integer compared = compareTimeStr(timeMillis, afterTimeStr);
        return compared != null && compared > 0;
    }

    /**
     * 检查时间戳是否在指定时间之前或等于
     */
    public static boolean isBeforeOrEqualTimeStr(Long timeMillis, String beforeTimeStr) {
        Integer compared = compareTimeStr(timeMillis, beforeTimeStr);
        return compared != null && compared <= 0;
    }

    /**
     * 检查时间戳是否在指定时间之后或等于
     */
    public static boolean isAfterOrEqualTimeStr(Long timeMillis, String afterTimeStr) {
        Integer compared = compareTimeStr(timeMillis, afterTimeStr);
        return compared != null && compared >= 0;
    }

    /**
     * 格式化时间差
     */
    public static String formatTimeDifference(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + "天" + (hours % 24) + "小时";
        } else if (hours > 0) {
            return hours + "小时" + (minutes % 60) + "分钟";
        } else if (minutes > 0) {
            return minutes + "分钟" + (seconds % 60) + "秒";
        } else {
            return seconds + "秒";
        }
    }

    /**
     * 检查是否在时间范围内
     */
    public static boolean isInTimeRange(Long timeMillis, String startTime, String endTime) {
        if (startTime == null || endTime == null) {
            return true;
        }
        
        boolean afterStart = isAfterOrEqualTimeStr(timeMillis, startTime);
        boolean beforeEnd = isBeforeOrEqualTimeStr(timeMillis, endTime);
        
        return afterStart && beforeEnd;
    }

    /**
     * 检查当前时间是否在时间范围内
     */
    public static boolean isNowInTimeRange(String startTime, String endTime) {
        return isInTimeRange(getCurrentTimeMillis(), startTime, endTime);
    }

    public static Boolean checkNowInTimeRange(String timeRange) {
        return checkInTimeRange(System.currentTimeMillis(), timeRange);
    }

    public static Boolean checkInTimeRange(Long timeMillis, List<String> timeRangeList) {
        for (String timeRange : timeRangeList) {
            if (checkInTimeRange(timeMillis, timeRange)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean isNowBeforeTimeStr(String beforeTimeStr) {
        return isBeforeTimeStr(System.currentTimeMillis(), beforeTimeStr);
    }

    public static Boolean isNowBeforeOrCompareTimeStr(String beforeTimeStr) {
        return isBeforeOrCompareTimeStr(System.currentTimeMillis(), beforeTimeStr);
    }

    public static Boolean isNowAfterOrCompareTimeStr(String afterTimeStr) {
        return isAfterOrCompareTimeStr(System.currentTimeMillis(), afterTimeStr);
    }

    public static Boolean isBeforeOrCompareTimeStr(Long timeMillis, String beforeTimeStr) {
        Integer compared = compareTimeStr(timeMillis, beforeTimeStr);
        if (compared != null) {
            return compared <= 0;
        }
        return false;
    }

    public static Boolean isAfterOrCompareTimeStr(Long timeMillis, String afterTimeStr) {
        Integer compared = compareTimeStr(timeMillis, afterTimeStr);
        if (compared != null) {
            return compared >= 0;
        }
        return false;
    }

    public static Integer isCompareTimeStr(Long timeMillis, String compareTimeStr) {
        try {
            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTimeInMillis(timeMillis);
            Calendar compareCalendar = getTodayCalendarByTimeStr(compareTimeStr);
            if (compareCalendar != null) {
                return timeCalendar.compareTo(compareCalendar);
            }
        } catch (Exception e) {
            Log.printStackTrace(e);
        }
        return null;
    }

    public static String getTimeStr(long ts) {
        return DateFormat.getTimeInstance().format(new Date(ts));
    }

    public static String getTimeStr() {
        return getTimeStr(System.currentTimeMillis());
    }

    public static String getDateStr() {
        return getDateStr(0);
    }

    public static String getDateStr(int plusDay) {
        Calendar c = Calendar.getInstance();
        if (plusDay != 0) {
            c.add(Calendar.DATE, plusDay);
        }
        return DateFormat.getDateInstance().format(c.getTime());
    }

    /**
     * 默认获取今天
     *
     * @return yyyy-MM-dd
     */
    public static String getDateStr2() {
        return getDateStr2(0);
    }

    /**
     * 默认获取今天
     *
     * @param plusDay 日期偏移量
     * @return yyyy-MM-dd
     */
    public static String getDateStr2(int plusDay) {
        Calendar c = Calendar.getInstance();
        if (plusDay != 0) {
            c.add(Calendar.DATE, plusDay);
        }
        Date date = c.getTime();

        // 使用固定格式 yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    public static Calendar getToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    public static Calendar getNow() {
        return Calendar.getInstance();
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定时间的周数
     *
     * @param dateTime 时间
     * @return 当前年的第几周
     */
    public static int getWeekNumber(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        // 设置周的第一天为周一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 比较第一个日历的天数小于第二个日历的天数
     *
     * @param firstCalendar  第一个日历
     * @param secondCalendar 第二个日历
     * @return Boolean 如果小于，则为true，否则为false
     */
    public static Boolean isLessThanSecondOfDays(Calendar firstCalendar, Calendar secondCalendar) {
        return (firstCalendar.get(Calendar.YEAR) < secondCalendar.get(Calendar.YEAR))
                || (firstCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR)
                && firstCalendar.get(Calendar.DAY_OF_YEAR) < secondCalendar.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * 比较第一个时间戳的天数是否小于第二个时间戳的天数
     *
     * @param firstTimestamp  第一个时间戳
     * @param secondTimestamp 第二个时间戳
     * @return Boolean 如果小于，则为true，否则为false
     */
    public static Boolean isLessThanSecondOfDays(Long firstTimestamp, Long secondTimestamp) {
        Calendar firstCalendar = getCalendarByTimeMillis(firstTimestamp);
        Calendar secondCalendar = getCalendarByTimeMillis(secondTimestamp);
        return isLessThanSecondOfDays(firstCalendar, secondCalendar);
    }

    /**
     * 通过时间戳比较传入的时间戳的天数是否小于当前时间戳的天数
     *
     * @param timestamp 时间戳
     * @return Boolean 如果小于当前时间戳所计算的天数，则为true，否则为false
     */
    public static Boolean isLessThanNowOfDays(Long timestamp) {
        return isLessThanSecondOfDays(getCalendarByTimeMillis(timestamp), getNow());
    }

    /**
     * 判断两个日历对象是否为同一天
     *
     * @param firstCalendar  第一个日历对象
     * @param secondCalendar 第二个日历对象
     * @return 两个日历对象是否为同一天
     */
    public static Boolean isSameDay(Calendar firstCalendar, Calendar secondCalendar) {
        return firstCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR)
                && firstCalendar.get(Calendar.DAY_OF_YEAR) == secondCalendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 判断两个时间戳是否为同一天
     *
     * @param firstTimestamp  第一个时间戳
     * @param secondTimestamp 第二个时间戳
     * @return 两个时间戳是否为同一天
     */
    public static Boolean isSameDay(Long firstTimestamp, Long secondTimestamp) {
        Calendar firstCalendar = getCalendarByTimeMillis(firstTimestamp);
        Calendar secondCalendar = getCalendarByTimeMillis(secondTimestamp);
        return isSameDay(firstCalendar, secondCalendar);
    }

    /**
     * 判断日历对象是否为今天
     *
     * @param calendar 日历对象
     * @return 日历对象是否为今天
     */
    public static Boolean isToday(Calendar calendar) {
        return isSameDay(getToday(), calendar);
    }

    /**
     * 判断时间戳是否为今天
     *
     * @param timestamp 时间戳
     * @return 时间戳是否为今天
     */
    public static Boolean isToday(Long timestamp) {
        return isToday(getCalendarByTimeMillis(timestamp));
    }

    @SuppressLint("SimpleDateFormat")
    public static DateFormat getCommonDateFormat() {
        return new SimpleDateFormat("dd日HH:mm:ss");
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCommonDate(Long timestamp) {
        return getCommonDateFormat().format(timestamp);
    }


    public static final ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }
    };

    public static final ThreadLocal<SimpleDateFormat> OTHER_DATE_TIME_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
        }
    };

    public static long timeToStamp(String timers) {
        Date d = new Date();
        long timeStemp;
        try {
            SimpleDateFormat simpleDateFormat = OTHER_DATE_TIME_FORMAT_THREAD_LOCAL.get();
            if (simpleDateFormat == null) {
                simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
            }
            Date newD = simpleDateFormat.parse(timers);
            if (newD != null) {
                d = newD;
            }
        } catch (ParseException ignored) {
        }
        timeStemp = d.getTime();
        return timeStemp;
    }

    /**
     * 获取格式化的日期 时间字符串yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getFormatDateTime() {
        SimpleDateFormat simpleDateFormat = DATE_TIME_FORMAT_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());
        }
        return simpleDateFormat.format(new Date());
    }


    /**
     * 获取格式化的日期符串yyyy-MM-dd
     *
     * @return
     */
    public static String getFormatDate() {
        return getFormatDateTime().split(" ")[0];
    }

    /**
     * 获取格式化的时间字符串HH:mm:ss
     *
     * @return
     */
    public static String getFormatTime() {
        return getFormatDateTime().split(" ")[1];
    }

    /**
     * 根据传入的格式化字符串获取格式化后的时间字符串
     *
     * @param offset 日期偏移量
     * @param format 格式化字符串
     * @return 格式化后的时间字符串
     */
    public static String getFormatTime(int offset, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }
}
