package com.mi.simple_alarm_clock_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.List;

@Dao
public interface SingleAlarmDao extends AlarmDao<SingleAlarm> {

    @Override
    @Query("SELECT * FROM single_alarms ORDER BY name DESC")
    List<SingleAlarm> getAllAlarms();

    @Override
    @Insert(entity = SingleAlarm.class)
    void insertNewAlarm(SingleAlarm alarmClock);

    @Override
    @Delete(entity = SingleAlarm.class)
    void deleteAlarm(SingleAlarm alarmClock);

    @Override
    @Query("SELECT * FROM single_alarms WHERE id == :id")
    SingleAlarm getAlarmById(int id);

    @Override
    @Query("SELECT EXISTS(SELECT 1 FROM single_alarms WHERE id == :id)")
    boolean doesAlarmExistById(int id);

    @Override
    @Update(entity = SingleAlarm.class)
    void updateAlarm(SingleAlarm alarmClock);
}
