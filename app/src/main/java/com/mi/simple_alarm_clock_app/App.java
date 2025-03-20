package com.mi.simple_alarm_clock_app;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.mi.simple_alarm_clock_app.database.AppDatabase;
import com.mi.simple_alarm_clock_app.database.ScheduledAlarmClockDao;

public class App extends Application  {

    private AppDatabase database;
    private ScheduledAlarmClockDao scheduledAlarmClockDao;

    private static App instance = null;

    // Singleton.
    public static App getInstance() {
        return instance;
    }

    public ScheduledAlarmClockDao getScheduledAlarmClockDao() {
        return database.getScheduledAlarmClockDao();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (instance == null) {
            instance = this;

            database = Room.databaseBuilder(
                    getApplicationContext(),
                    AppDatabase.class,
                    "database.db"
            ).build();
        }
    }


}
