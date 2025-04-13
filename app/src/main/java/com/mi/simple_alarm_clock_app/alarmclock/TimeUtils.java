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

    public static long getNextRepeatingAlarmDateTime(RepeatingAlarm alarm) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarm.getTimeInMillis());

        int nextDayOfWeek = 0;
        while (calendar.get(Calendar.DAY_OF_WEEK) != nextDayOfWeek) {
            int i = calendar.get(Calendar.DAY_OF_WEEK);

            if (alarm.isSunday() && i == Calendar.SUNDAY) nextDayOfWeek = 1;
            else if (alarm.isMonday() && i == Calendar.MONDAY) nextDayOfWeek = 2;
            else if (alarm.isTuesday() && i == Calendar.TUESDAY) nextDayOfWeek = 3;
            else if (alarm.isWednesday() && i == Calendar.WEDNESDAY) nextDayOfWeek = 4;
            else if (alarm.isThursday() && i == Calendar.THURSDAY) nextDayOfWeek = 5;
            else if (alarm.isFriday() && i == Calendar.FRIDAY) nextDayOfWeek = 6;
            else if (alarm.isSaturday() && i == Calendar.SATURDAY) nextDayOfWeek = 7;

            if (nextDayOfWeek == 0) calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        int hour = TimeUtils.getHourFromMillis(alarm.getTimeInMillis());
        int minute = TimeUtils.getMinuteFromMillis(alarm.getTimeInMillis());

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
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
