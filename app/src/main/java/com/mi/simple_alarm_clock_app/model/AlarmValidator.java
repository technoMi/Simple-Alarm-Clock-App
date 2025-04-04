package com.mi.simple_alarm_clock_app.model;

import java.util.ArrayList;

public class AlarmValidator {
    public static boolean isValidate(Alarm a) {
        if (a.getDateTimeInMillis() > 0) {
            return true;
        } else return (a.isMonday() || a.isTuesday() || a.isWednesday() || a.isThursday()
                || a.isFriday() || a.isSaturday() || a.isSunday());
    }
}
