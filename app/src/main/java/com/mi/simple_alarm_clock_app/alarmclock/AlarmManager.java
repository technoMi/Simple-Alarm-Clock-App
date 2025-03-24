package com.mi.simple_alarm_clock_app.alarmclock;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mi.simple_alarm_clock_app.ui.activities.AlarmActivity;
import com.mi.simple_alarm_clock_app.ui.activities.MainActivity;

public class AlarmManager {

    private final Context context;

    private final android.app.AlarmManager alarmManager;

    public AlarmManager(Context context) {
        this.context = context;
        alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarmClock(long timeInMillis) {

        android.app.AlarmManager.AlarmClockInfo alarmClockInfo = new android.app.AlarmManager.AlarmClockInfo(
                timeInMillis, getAlarmInfoIntent()
        );

        alarmManager.setAlarmClock(alarmClockInfo, getAlarmPendingIntent());
    }

    private PendingIntent getAlarmInfoIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private PendingIntent getAlarmPendingIntent() {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE);
    }
}
