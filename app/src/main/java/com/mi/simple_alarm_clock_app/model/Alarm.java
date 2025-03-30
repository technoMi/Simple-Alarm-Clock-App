package com.mi.simple_alarm_clock_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm")
public class Alarm {

    public Alarm(
            int id,
            String name,
            long timeInMillis,
            boolean isEnabled,
            boolean isMonday,
            boolean isTuesday,
            boolean isWednesday,
            boolean isThursday,
            boolean isFriday,
            boolean isSaturday,
            boolean isSunday
            ) {
        this.id = id;
        this.name = name;
        this.timeInMillis = timeInMillis;
        this.isEnabled = isEnabled;
        this.isMonday = isMonday;
        this.isTuesday = isTuesday;
        this.isWednesday = isWednesday;
        this.isThursday = isThursday;
        this.isFriday = isFriday;
        this.isSaturday = isSaturday;
        this.isSunday = isSunday;
    }

    @ColumnInfo
    @PrimaryKey
    private final int id;

    @ColumnInfo
    private final String name;

    @ColumnInfo
    private final long timeInMillis;

    @ColumnInfo
    private final boolean isEnabled;

    @ColumnInfo
    private final boolean isMonday;

    @ColumnInfo
    private final boolean isTuesday;

    @ColumnInfo
    private final boolean isWednesday;

    @ColumnInfo
    private final boolean isThursday;

    @ColumnInfo
    private final boolean isFriday;

    @ColumnInfo
    private final boolean isSaturday;

    @ColumnInfo
    private final boolean isSunday;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isMonday() {
        return isMonday;
    }

    public boolean isTuesday() {
        return isTuesday;
    }

    public boolean isWednesday() {
        return isWednesday;
    }

    public boolean isThursday() {
        return isThursday;
    }

    public boolean isFriday() {
        return isFriday;
    }

    public boolean isSaturday() {
        return isSaturday;
    }

    public boolean isSunday() {
        return isSunday;
    }
}
