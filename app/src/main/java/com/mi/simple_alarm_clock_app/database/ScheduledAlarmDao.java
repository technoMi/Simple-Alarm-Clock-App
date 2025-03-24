package com.mi.simple_alarm_clock_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mi.simple_alarm_clock_app.model.ScheduledAlarm;

import java.util.List;

@Dao
public interface ScheduledAlarmDao {
    @Query("SELECT * FROM scheduled_alarm_clock")
    List<ScheduledAlarm> getAllAlarmClocks();

    @Insert(entity = ScheduledAlarm.class)
    void insertNewScheduledAlarmClock(ScheduledAlarm alarmClock);

    @Delete(entity = ScheduledAlarm.class)
    void deleteScheduledAlarmClock(ScheduledAlarm alarmClock);

    @Query("SELECT EXISTS(SELECT 1 FROM scheduled_alarm_clock WHERE id == :requestedId)")
    boolean doesItemExistById(int requestedId);
}
