package com.mi.simple_alarm_clock_app.model;

import androidx.room.Entity;

import java.util.Calendar;

@Entity(tableName = "single_alarms")
public class SingleAlarm extends Alarm {

    public SingleAlarm(int id, String name, long timeInMillis, boolean isEnabled) {
        super(id, name, timeInMillis, isEnabled);
    }

    public SingleAlarm() {
        // empty
    }

    @Override
    public void calculateNextTriggerTime() {
        this.setEnabled(false);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_WEEK, 1);

        this.setTimeInMillis(calendar.getTimeInMillis());
    }
}
