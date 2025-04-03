package com.mi.simple_alarm_clock_app.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.receivers.Actions;
import com.mi.simple_alarm_clock_app.receivers.AlarmReceiver;
import com.mi.simple_alarm_clock_app.ui.activities.MainActivity;

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
}
