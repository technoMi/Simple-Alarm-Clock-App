package com.mi.simple_alarm_clock_app.model;

public interface ITimeRecalculation {

    /*
    This method is used to recalculate the alarm response time after
    the alarm has been triggered.
    */
    void calculateNextTriggerTime();
}
