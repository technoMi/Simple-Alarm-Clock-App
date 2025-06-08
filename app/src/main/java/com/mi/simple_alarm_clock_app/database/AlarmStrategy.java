package com.mi.simple_alarm_clock_app.database;

public interface AlarmStrategy<T> {
    AlarmDao<T> getDao();
}
