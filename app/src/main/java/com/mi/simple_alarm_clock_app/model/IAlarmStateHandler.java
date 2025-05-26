package com.mi.simple_alarm_clock_app.model;

public interface IAlarmStateHandler {
    /*
    Use this method in cases where you need to
    perform actions before the alarm enabled in the UI.
    */
    void doBeforeAlarmTurnedOn();

    /*
    Use this method in cases where you need to
    perform actions after an alarm has been triggered.
    */
    void doAfterAlarmTriggered();

    /*
    This method is used to recalculate the alarm response time after
    the alarm has been triggered.
    */
    void calculateNextTriggerTime();
}
