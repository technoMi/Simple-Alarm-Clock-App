package com.mi.simple_alarm_clock_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scheduled_alarm_clock")
public class ScheduledAlarmClock {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "time_of_day")
    public long timeOfDay;

    @ColumnInfo(name = "enabled")
    public boolean isEnabled;
}
