package com.mi.simple_alarm_clock_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mi.simple_alarm_clock_app.model.ScheduledAlarmClock;

import java.util.List;

@Dao
public interface ScheduledAlarmClockDao {
    @Query("SELECT * FROM scheduled_alarm_clock")
    List<ScheduledAlarmClock> getAllAlarmClocks();

    @Insert(entity = ScheduledAlarmClock.class)
    void insertNewScheduledAlarmClock(ScheduledAlarmClock alarmClock);

    @Delete(entity = ScheduledAlarmClock.class)
    void deleteScheduledAlarmClock(ScheduledAlarmClock alarmClock);
}
