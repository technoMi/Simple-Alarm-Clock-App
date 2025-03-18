package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.room.Room;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.AppDatabase;
import com.mi.simple_alarm_clock_app.model.ScheduledAlarmClock;
import com.mi.simple_alarm_clock_app.database.ScheduledAlarmClockDao;

import java.util.List;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            ScheduledAlarmClockDao databaseDao = (
                    Room.databaseBuilder(
                                    context,
                                    AppDatabase.class,
                                    "database.db"
                            )
                            .build()
                            .getScheduledAlarmClockDao()
            );

            new Thread() {
                @Override
                public void run() {
                    super.run();

                    List<ScheduledAlarmClock> alarmClocks = databaseDao.getAllAlarmClocks();

                    AlarmClockManager manager = new AlarmClockManager(context);

                    for (ScheduledAlarmClock alarmClock : alarmClocks) {
                        manager.setAlarmClock(alarmClock.timeOfDay);
                    }
                }
            }.start();
        }
    }
}
