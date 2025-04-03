package com.mi.simple_alarm_clock_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm")
public class Alarm {

    public Alarm(
            int id,
            String name,
            int hour,
            int minute,
            long dateTimeInMillis,
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
        this.hour = hour;
        this.minute = minute;
        this.timeInMillis = dateTimeInMillis;
        this.isEnabled = isEnabled;
        this.isMonday = isMonday;
        this.isTuesday = isTuesday;
        this.isWednesday = isWednesday;
        this.isThursday = isThursday;
        this.isFriday = isFriday;
        this.isSaturday = isSaturday;
        this.isSunday = isSunday;
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
    private int hour;

    @ColumnInfo
    private int minute;

    @ColumnInfo
    private long dateTimeInMillis;

    @ColumnInfo
    private boolean isEnabled;

    @ColumnInfo
    private boolean isMonday;

    @ColumnInfo
    private boolean isTuesday;

    @ColumnInfo
    private boolean isWednesday;

    @ColumnInfo
    private boolean isThursday;

    @ColumnInfo
    private boolean isFriday;

    @ColumnInfo
    private boolean isSaturday;

    @ColumnInfo
    private boolean isSunday;

    // Getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public long getDayTimeInMillis() {
        return dateTimeInMillis;
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

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDayTimeInMillis(long timeInMillis) {
        this.dateTimeInMillis = timeInMillis;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }

    public void setTuesday(boolean tuesday) {
        isTuesday = tuesday;
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }

    public void setThursday(boolean thursday) {
        isThursday = thursday;
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }

    public void setSunday(boolean sunday) {
        isSunday = sunday;
    }
}
