package com.mi.simple_alarm_clock_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;

import java.util.List;

@Dao
public interface RepeatingAlarmDao extends AlarmDao<RepeatingAlarm> {

    @Override
    @Query("SELECT * FROM repeating_alarms ORDER BY name DESC")
    List<RepeatingAlarm> getAllAlarms();

    @Override
    @Insert(entity = RepeatingAlarm.class)
    void insertNewAlarm(RepeatingAlarm alarmClock);

    @Override
    @Delete(entity = RepeatingAlarm.class)
    void deleteAlarm(RepeatingAlarm alarmClock);

    @Override
    @Query("SELECT * FROM repeating_alarms WHERE id == :id")
    RepeatingAlarm getAlarmById(int id);

    @Override
    @Query("SELECT EXISTS(SELECT 1 FROM repeating_alarms WHERE id == :id)")
    boolean doesAlarmExistById(int id);

    @Override
    @Update(entity = RepeatingAlarm.class)
    void updateAlarm(RepeatingAlarm alarmClock);
}
