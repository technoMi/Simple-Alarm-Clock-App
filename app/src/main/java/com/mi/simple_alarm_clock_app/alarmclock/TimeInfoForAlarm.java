package com.mi.simple_alarm_clock_app.alarmclock;

public class TimeInfoForAlarm {
    private int hour = 0;
    private int minute = 0;
    private long selectedDayInMillis = 0;
    private String dateTittle;

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

    public long getSelectedDayInMillis() {
        return selectedDayInMillis;
    }

    public void setSelectedDayInMillis(long selectedDayInMillis) {
        this.selectedDayInMillis = selectedDayInMillis;
    }

    public String getDateTittle() {
        return dateTittle;
    }

    public void setDateTittle(String dateTittle) {
        this.dateTittle = dateTittle;
    }
}
