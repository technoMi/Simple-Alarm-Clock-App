package com.mi.simple_alarm_clock_app.model;

public interface IAlarmStateHandler {
    void doBeforeAlarmTurnedOn();
    void doAfterAlarmTriggered();
}
