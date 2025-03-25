package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

//            ScheduledAlarmDao databaseDao = App.getInstance().getScheduledAlarmClockDao();
//
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//
//                    List<ScheduledAlarm> alarmClocks = databaseDao.getAllAlarmClocks();
//
//                    AlarmManager manager = new AlarmManager(context);
//
//                    for (ScheduledAlarm alarmClock : alarmClocks) {
//                        manager.setAlarmClock(alarmClock.timeOfDay);
//                    }
//                }
//            }.start();
        }
    }
}
