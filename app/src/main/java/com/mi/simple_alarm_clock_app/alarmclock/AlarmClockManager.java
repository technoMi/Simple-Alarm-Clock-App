package com.mi.simple_alarm_clock_app.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.receivers.Actions;
import com.mi.simple_alarm_clock_app.receivers.AlarmReceiver;
import com.mi.simple_alarm_clock_app.ui.activities.MainActivity;

import java.util.Calendar;

public class AlarmClockManager {

    private final Context context;

    private final android.app.AlarmManager alarmManager;

    public AlarmClockManager(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarmClockInSystemManager(Alarm alarmInfo) {
        setPendingAlarm(alarmInfo);
    }

    private void setPendingAlarm(Alarm alarmInfo) {
        PendingIntent alarmAppInfo = getAlarmAppInfoIntent();

        int hour = alarmInfo.getHour();
        int minute = alarmInfo.getMinute();
        long dateTimeInMillis = alarmInfo.getDateTimeInMillis();
        long time = Tools.getTimeInMillis(dateTimeInMillis, hour, minute);

        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
                time, alarmAppInfo
        );

        PendingIntent alarmPendingIntent = getAlarmPendingIntent(alarmInfo);
        alarmManager.setAlarmClock(alarmClockInfo, alarmPendingIntent);
    }

    private PendingIntent getAlarmAppInfoIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private PendingIntent getAlarmPendingIntent(Alarm alarmInfo) {
        Intent intent = new Intent(context, AlarmReceiver.class);

        int id = alarmInfo.getId();

        intent.putExtra("id", alarmInfo.getId());
        intent.setAction(Actions.ALARM_ACTION);

        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    public void canselAlarmClockInSystemManager(Alarm alarmInfo) {

        PendingIntent alarmPendingIntent = getAlarmPendingIntent(alarmInfo);

        alarmManager.cancel(alarmPendingIntent);
    }

    public void recalculateTimeForAlarmClock(Alarm alarm) {

        DatabaseManager dbManager = new DatabaseManager();

        canselAlarmClockInSystemManager(alarm);

        long alarmDateTimeInMillis = alarm.getDateTimeInMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarmDateTimeInMillis);

        int nextDayOfWeek = 0;
        do {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            int i = calendar.get(Calendar.DAY_OF_WEEK);

            if (alarm.isSunday() && i == Calendar.SUNDAY) nextDayOfWeek = 1;
            if (alarm.isMonday() && i == Calendar.MONDAY) nextDayOfWeek = 2;
            if (alarm.isSaturday() && i == Calendar.SATURDAY) nextDayOfWeek = 3;
            if (alarm.isWednesday() && i == Calendar.WEDNESDAY) nextDayOfWeek = 4;
            if (alarm.isThursday() && i == Calendar.THURSDAY) nextDayOfWeek = 5;
            if (alarm.isFriday() && i == Calendar.FRIDAY) nextDayOfWeek = 6;
            if (alarm.isSaturday() && i == Calendar.SATURDAY) nextDayOfWeek = 7;
        } while (calendar.get(Calendar.DAY_OF_WEEK) != nextDayOfWeek);

        alarm.setDateTimeInMillis(calendar.getTimeInMillis());

        setAlarmClockInSystemManager(alarm);
        dbManager.updateAlarmClock(alarm);
    }
}
