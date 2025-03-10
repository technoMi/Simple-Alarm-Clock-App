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
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private PendingIntent getAlarmPendingIntent() {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(Constants.INTENT_TO_SHOW_ALARM_CLOCK_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_IMMUTABLE);
    }
}
