package com.mi.simple_alarm_clock_app.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.ScheduledAlarm;
import com.mi.simple_alarm_clock_app.receivers.Actions;
import com.mi.simple_alarm_clock_app.receivers.AlarmReceiver;
import com.mi.simple_alarm_clock_app.ui.activities.MainActivity;

import java.util.ArrayList;

public class AlarmClockManager {

    private final Context context;

    private final android.app.AlarmManager alarmManager;

    public AlarmClockManager(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void saveAlarmClock(String name, long dayTimeInMillis, int hour, int minute,
                               ArrayList<String> daysOfWeek) {

        int id = DatabaseManager.getNewItemID();
        long time = Tools.getTimeInMillis(dayTimeInMillis, hour, minute);

        setPendingAlarm(name, id, time, daysOfWeek);
        saveAlarmToDatabase(name, id, time, daysOfWeek);
    }

    private void setPendingAlarm(String name, int id, long time, ArrayList<String> daysOfWeek) {
        PendingIntent alarmInfo = getAlarmInfoIntent();
        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
                time, alarmInfo
        );


        PendingIntent alarmPendingIntent = getAlarmPendingIntent(id, daysOfWeek);
        alarmManager.setAlarmClock(alarmClockInfo, alarmPendingIntent);
    }

    private PendingIntent getAlarmInfoIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private PendingIntent getAlarmPendingIntent(int id, ArrayList<String> daysOfWeek) {
        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra("id", id);
        intent.putStringArrayListExtra("daysOfWeek", daysOfWeek);
        intent.setAction(Actions.ALARM_ACTION);

        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private void saveAlarmToDatabase(String name, int id, long time,
                                     ArrayList<String> daysOfWeek) {
        ScheduledAlarm alarm = new ScheduledAlarm();
        alarm.id = id;
        alarm.timeInMillis = time;
        alarm.daysOfWeek = daysOfWeek;
        alarm.isEnabled = true;
        alarm.name = (name != null) ? name : "";

        new Thread() {
            @Override
            public void run() {
                super.run();
                App.getInstance().getScheduledAlarmClockDao().insertNewScheduledAlarmClock(alarm);
            }
        }.start();
    }
}
