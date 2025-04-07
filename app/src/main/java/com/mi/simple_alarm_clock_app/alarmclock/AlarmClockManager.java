package com.mi.simple_alarm_clock_app.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;
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

    private void setPendingAlarm(Alarm alarm) {
        PendingIntent alarmAppInfo = getAlarmAppInfoIntent();

        int hour = Tools.getHourFromMillis(alarm.getTimeInMillis());
        int minute = Tools.getMinuteFromMillis(alarm.getTimeInMillis());
        long alarmTime = 0;

        if (alarm instanceof SingleAlarm) {
            alarmTime = alarm.getTimeInMillis();
        }
        if (alarm instanceof RepeatingAlarm) {
            alarmTime = TimeUtils.getNextRepeatingAlarmDateTime(alarm);
        }

        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
                alarmTime, alarmAppInfo
        );

        PendingIntent alarmPendingIntent = getAlarmPendingIntent(alarm);
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
        setAlarmClockInSystemManager(alarm);
    }
}
