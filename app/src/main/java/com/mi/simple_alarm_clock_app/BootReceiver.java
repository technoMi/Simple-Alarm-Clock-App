package com.mi.simple_alarm_clock_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mi.simple_alarm_clock_app.database.AppDatabase;
import com.mi.simple_alarm_clock_app.database.ScheduledAlarmClock;
import com.mi.simple_alarm_clock_app.database.ScheduledAlarmClockDao;

import java.util.List;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "database.db").build();
                ScheduledAlarmClockDao databaseDao = database.getScheduledAlarmClockDao();

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
