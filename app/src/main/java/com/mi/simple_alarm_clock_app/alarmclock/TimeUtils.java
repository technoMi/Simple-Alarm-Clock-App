package com.mi.simple_alarm_clock_app.alarmclock;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {
    public static long getAlarmTimeInMillis(long dateInMillis, int hour, int minute) {
        if (dateInMillis != 0 && hour != -1 && minute != -1) {
            Calendar calendar = Calendar.getInstance();

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
