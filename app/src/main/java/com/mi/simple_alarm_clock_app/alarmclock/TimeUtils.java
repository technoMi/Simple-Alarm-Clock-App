package com.mi.simple_alarm_clock_app.alarmclock;

import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;

import java.sql.Time;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {
    public static long getAlarmTimeInMillis(long dateInMillis, int hour, int minute) {
        if (dateInMillis != 0 && hour != -1 && minute != -1) {
            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
            calendar.setTimeInMillis(dateInMillis);

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.getTimeInMillis();
        } else {
            return 0;
        }
    }

    public static long getTodayDateTimeInMillis() {
        Calendar calendar = Calendar.getInstance();

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);
        calendar.set(Calendar.DAY_OF_MONTH, currentDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    public static int getHourFromMillis(long time) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeZone(TimeZone.getDefault());

        calendar.setTimeInMillis(time);

        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinuteFromMillis(long time) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(time);

        return calendar.get(Calendar.MINUTE);
    }
}
