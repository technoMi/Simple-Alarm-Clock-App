package com.mi.simple_alarm_clock_app;

import android.app.Application;

import androidx.room.Room;

import com.mi.simple_alarm_clock_app.database.AppDatabase;
import com.mi.simple_alarm_clock_app.database.ScheduledAlarmClockDao;

public class App extends Application  {

    private AppDatabase database;
    private ScheduledAlarmClockDao scheduledAlarmClockDao;

    private static App instance;

    // Singleton.
    public static App getInstance() {
        return instance;
    }

    public ScheduledAlarmClockDao getScheduledAlarmClockDao() {
        return scheduledAlarmClockDao;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        database = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "database.db"
        ).build();
    }
}
