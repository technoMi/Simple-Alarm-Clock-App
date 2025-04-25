package com.mi.simple_alarm_clock_app;

import android.app.Application;

import androidx.room.Room;

import com.mi.simple_alarm_clock_app.database.AppDatabase;
import com.mi.simple_alarm_clock_app.database.RepeatingAlarmDao;
import com.mi.simple_alarm_clock_app.database.SingleAlarmDao;

public class App extends Application  {

    private AppDatabase database;

    private static App instance = null;

    // Singleton.
    public static App getInstance() {
        return instance;
    }

    public SingleAlarmDao getSingleAlarmDao() {
        return database.getSingleAlarmDao();
    }

    public RepeatingAlarmDao getRepeatingAlarmDao() {
        return database.getRepeatingAlarmDao();
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
            ).allowMainThreadQueries().build();
        }
    }
}