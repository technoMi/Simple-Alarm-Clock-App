package com.mi.simple_alarm_clock_app.database;

import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Entity
public interface AlarmDao<T> {
    @Query("")
    List<T> getAllAlarms();

    @Insert()
    void insertNewAlarm(T alarmClock);

    @Delete()
    void deleteAlarm(T alarmClock);

    @Query("")
    T getAlarmById(int id);

    @Query("")
    boolean doesAlarmExistById(int id);

    @Update()
    void updateAlarm(T alarmClock);
}
