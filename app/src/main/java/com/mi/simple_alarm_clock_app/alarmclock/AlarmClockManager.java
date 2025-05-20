package com.mi.simple_alarm_clock_app.alarmclock;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.app.AlarmManager;

import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.receivers.Actions;
import com.mi.simple_alarm_clock_app.receivers.AlarmReceiver;
import com.mi.simple_alarm_clock_app.ui.activities.MainActivity;

public class AlarmClockManager {

    private final String TAG = "Debug.AlarmClockManager";

    private final Context context;

    private final AlarmManager alarmManager;

    public AlarmClockManager(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarmInSystemManager(Alarm alarm) {
        setPendingAlarm(alarm);
    }

    private void setPendingAlarm(Alarm alarm) {
        PendingIntent alarmAppInfo = getAlarmAppInfoIntent();

        long alarmTime = alarm.getTimeInMillis();

        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
                alarmTime, alarmAppInfo
        );

        PendingIntent alarmPendingIntent = getAlarmPendingIntent(alarm);

        alarmManager.setAlarmClock(alarmClockInfo, alarmPendingIntent);

        alarmManager.getNextAlarmClock();;
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

        Log.d(TAG, TAG + " New alarm pending Intent. ID: " + id);

        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    public void canselAlarmInSystemManager(Alarm alarmInfo) {
        PendingIntent alarmPendingIntent = getAlarmPendingIntent(alarmInfo);
        alarmManager.cancel(alarmPendingIntent);
    }

    public void resetAlarm(Alarm alarm) {
        if (alarm.isEnabled()) {
            Log.d(TAG, TAG + " Recalculate time for alarm. ID: " + alarm.getId());
            setAlarmInSystemManager(alarm);
        }
    }

    public long getNextAlarmTimeInMillis() throws NullPointerException {
        AlarmManager.AlarmClockInfo alarmClockInfo = alarmManager.getNextAlarmClock();

        long timeInMillis = alarmClockInfo.getTriggerTime();

        return timeInMillis;
    }
}
