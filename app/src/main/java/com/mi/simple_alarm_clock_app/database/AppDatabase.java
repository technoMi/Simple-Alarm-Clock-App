package com.mi.simple_alarm_clock_app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

@Database(entities = {RepeatingAlarm.class, SingleAlarm.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SingleAlarmDao getSingleAlarmDao();
    public abstract RepeatingAlarmDao getRepeatingAlarmDao();
}
