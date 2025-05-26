package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.model.Alarm;

import java.util.List;

public interface AlarmRepository<T extends Alarm> {
    List<T> getAllAlarms();
    void insertNewAlarm(T alarm);
    void deleteAlarm(T alarm);
    T getAlarmByID(int id);
    void doesItemExistById(int id);
    void updateAlarm(T alarm);
}