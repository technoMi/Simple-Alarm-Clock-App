package com.mi.simple_alarm_clock_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mi.simple_alarm_clock_app.model.Alarm;

import java.util.List;

@Dao
public interface ScheduledAlarmDao {
    @Query("SELECT * FROM Alarm")
    List<Alarm> getAllAlarmClocks();

    @Insert(entity = Alarm.class)
    void insertNewScheduledAlarmClock(Alarm alarmClock);

    @Delete(entity = Alarm.class)
    void deleteScheduledAlarmClock(Alarm alarmClock);

    @Query("SELECT * FROM Alarm WHERE id == :id")
    Alarm getItemById(int id);

    @Query("SELECT EXISTS(SELECT 1 FROM Alarm WHERE id == :id)")
    boolean doesItemExistById(int id);
}
