package com.dqztc.yourbirthday;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseUtil {

    public static final String TIME_YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String TIME_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * dp2px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static ColorStateList toColorStateList(@ColorInt int normalColor, @ColorInt int pressedColor) {
        return toColorStateList(normalColor, pressedColor, pressedColor, normalColor);
    }

    /**
     * 对TextView、Button等设置不同状态时其文字颜色。
     * 参见：http://blog.csdn.net/sodino/article/details/6797821
     * Modified by liyujiang at 2015.08.13
     */
    public static ColorStateList toColorStateList(@ColorInt int normalColor, @ColorInt int pressedColor,
                                                  @ColorInt int focusedColor, @ColorInt int unableColor) {
        int[] colors = new int[]{pressedColor, focusedColor, normalColor, focusedColor, unableColor, normalColor};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }


    /**
     * 获取Calendar
     *
     * @param time 时间字符串
     * @return Calendar
     */
    public static Calendar TransCalendar(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar caldate;
        Date date = null;
        try {
            date = sdf.parse(time); // 将传入的时间参数转化为日期
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        caldate = Calendar.getInstance();
        caldate.setTime(date);
        return caldate;
    }

    public static Calendar TransCalendar2(String day) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date tmpDate = format.parse(day);


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tmpDate);

            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Calendar TransCalendar3(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date tmpDate = format.parse(time);


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tmpDate);

            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Calendar TransCalendarFormat(String time, String formatstr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatstr);

            Date tmpDate = format.parse(time);


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tmpDate);

            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Calendar getCalendarFromTimeStr(String startTime) {
        Calendar startCalendar;
        if (startTime.length() == 10){
            startCalendar = TransCalendar2(startTime);
        } else  if (startTime.length() == 19) {
            startCalendar = TransCalendar(startTime);
        } else  if (startTime.length() == 16) {
            startCalendar = TransCalendarFormat(startTime, BaseUtil.TIME_YMD_HM);
        } else {
            startCalendar = TransCalendarFormat(startTime, "yyyy-MM");
        }
        return startCalendar;
    }
}
