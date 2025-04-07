package com.mi.simple_alarm_clock_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "repeating_alarms")
public class RepeatingAlarm extends Alarm {

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


    public boolean isMonday() {
        return isMonday;
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }

    public boolean isTuesday() {
        return isTuesday;
    }

    public void setTuesday(boolean tuesday) {
        isTuesday = tuesday;
    }

    public boolean isWednesday() {
        return isWednesday;
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }

    public boolean isThursday() {
        return isThursday;
    }

    public void setThursday(boolean thursday) {
        isThursday = thursday;
    }

    public boolean isFriday() {
        return isFriday;
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }

    public boolean isSaturday() {
        return isSaturday;
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }

    public boolean isSunday() {
        return isSunday;
    }

    public void setSunday(boolean sunday) {
        isSunday = sunday;
    }
}
