package com.mi.simple_alarm_clock_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public abstract class Alarm implements ITimeRecalculation, IAlarmStateHandler {

    public Alarm(
            int id,
            String name,
            long timeInMillis,
            boolean isEnabled
    ) {
        this.id = id;
        this.name = name;
        this.timeInMillis = timeInMillis;
        this.isEnabled = isEnabled;
    }

    public Alarm() {
        // empty
    }

    @ColumnInfo
    @PrimaryKey
    private int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private long timeInMillis;

    @ColumnInfo
    private boolean isEnabled;

    // Getters

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

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
