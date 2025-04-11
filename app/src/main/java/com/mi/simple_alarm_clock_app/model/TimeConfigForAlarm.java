package com.mi.simple_alarm_clock_app.model;

public class TimeConfigForAlarm {
    private int hour = -1;
    private int minute = -1;
    private int dateTimeInMillis = -1;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDateTimeInMillis() {
        return dateTimeInMillis;
    }

    public void setDateTimeInMillis(int dateTimeInMillis) {
        this.dateTimeInMillis = dateTimeInMillis;
    }
}
