package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mi.simple_alarm_clock_app.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.database.ScheduledAlarmClock;
import com.mi.simple_alarm_clock_app.database.ScheduledAlarmClockDao;

import java.util.List;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

                ScheduledAlarmClockDao databaseDao = (
                        DatabaseManager.getDatabase(context).getScheduledAlarmClockDao()
                );

                new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        List<ScheduledAlarmClock> alarmClocks = databaseDao.getAll();

                        AlarmClockManager manager = new AlarmClockManager(context);

                        for (ScheduledAlarmClock alarmClock : alarmClocks) {
                            manager.setAlarmClock(alarmClock.timeOfDay);
                        }
                    }
                }.start();
        }
    }
}
