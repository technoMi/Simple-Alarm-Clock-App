package com.mi.simple_alarm_clock_app.model;

import androidx.room.Entity;

@Entity(tableName = "single_alarms")
public class SingleAlarm extends Alarm {

    public SingleAlarm(int id, String name, long timeInMillis, boolean isEnabled) {
        super(id, name, timeInMillis, isEnabled);
    }

    public SingleAlarm() {
        // empty
    }
}
