package com.mi.simple_alarm_clock_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmClockManager {

    private final Context context;

    private final AlarmManager alarmManager;

    public AlarmClockManager(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarmClock(long timeInMillis) {

        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
                timeInMillis, getAlarmInfo()
        );

        alarmManager.setAlarmClock(alarmClockInfo, getAlarmPendingIntent());
    }

    private PendingIntent getAlarmInfo() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getAlarmPendingIntent() {
        Intent intent = new Intent(context, AlarmClockActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
