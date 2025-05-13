package com.mi.simple_alarm_clock_app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.mi.simple_alarm_clock_app.alarmclock.TimeUtils;

import java.util.Calendar;

@Entity(tableName = "repeating_alarms")
public class RepeatingAlarm extends Alarm {

    public RepeatingAlarm(
            int id,
            String name,
            int hour,
            int minute,
            boolean isEnabled,
            boolean isMonday,
            boolean isTuesday,
            boolean isWednesday,
            boolean isThursday,
            boolean isFriday,
            boolean isSaturday,
            boolean isSunday) {

        super(
                id,
                name,
                TimeUtils.getAlarmTimeInMillis(
                    TimeUtils.getTodayDateTimeInMillis(),
                    hour,
                    minute
                ),
                isEnabled);
        this.isMonday = isMonday;
        this.isTuesday = isTuesday;
        this.isWednesday = isWednesday;
        this.isThursday = isThursday;
        this.isFriday = isFriday;
        this.isSaturday = isSaturday;
        this.isSunday = isSunday;
    }

    public RepeatingAlarm() {
        // empty
    }

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

    @Override
    public void doBeforeAlarmTurnedOn() {

    }

    @Override
    public void doAfterAlarmTriggered() {

    }

    @Override
    public void calculateNextTriggerTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getTimeInMillis());

        int i = 0;
        do {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            i = calendar.get(Calendar.DAY_OF_WEEK);
        } while (!(
                (isMonday() && i == Calendar.MONDAY) ||
                (isTuesday() && i == Calendar.TUESDAY) ||
                (isWednesday() && i == Calendar.WEDNESDAY) ||
                (isThursday() && i == Calendar.THURSDAY) ||
                (isFriday() && i == Calendar.FRIDAY) ||
                (isSaturday() && i == Calendar.SATURDAY) ||
                (isSunday() && i == Calendar.SUNDAY)
        ));

        int hour = TimeUtils.getHourFromMillis(getTimeInMillis());
        int minute = TimeUtils.getMinuteFromMillis(getTimeInMillis());

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        setTimeInMillis(calendar.getTimeInMillis());
    }
}
