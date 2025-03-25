package com.mi.simple_alarm_clock_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "scheduled_alarm_clock")
public class ScheduledAlarm {
    @ColumnInfo(name = "id")
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "days_of_week")
    public ArrayList<String> daysOfWeek;

    @ColumnInfo(name = "time_in_millis")
    public long timeInMillis;

    @ColumnInfo(name = "enabled")
    public boolean isEnabled;
}
