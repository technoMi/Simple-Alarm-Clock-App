package com.mi.simple_alarm_clock_app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ScheduledAlarmClock.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScheduledAlarmClockDao getScheduledAlarmClockDao();
}
