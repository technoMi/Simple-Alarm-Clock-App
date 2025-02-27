package com.mi.simple_alarm_clock_app.entities;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class MyTimePicker {

    public MaterialTimePicker getTimePickerFragment() {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("")
                .setHour(0)
                .setMinute(0)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build();

        return  timePicker;
    }
}
