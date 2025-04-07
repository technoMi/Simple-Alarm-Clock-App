package com.mi.simple_alarm_clock_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;

import java.util.List;

@Dao
public interface RepeatingAlarmDao {

    @Query("SELECT * FROM repeating_alarms ORDER BY name DESC")
    List<RepeatingAlarm> getAllRepeatingAlarmClocks();

    @Insert(entity = RepeatingAlarm.class)
    void insertNewScheduledRepeatingAlarmClock(RepeatingAlarm alarmClock);

    @Delete(entity = RepeatingAlarm.class)
    void deleteScheduledRepeatingAlarmClock(RepeatingAlarm alarmClock);

    @Query("SELECT * FROM repeating_alarms WHERE id == :id")
    RepeatingAlarm getItemById(int id);

    @Query("SELECT EXISTS(SELECT 1 FROM repeating_alarms WHERE id == :id)")
    boolean doesItemExistById(int id);

    @Update(entity = RepeatingAlarm.class)
    void updateScheduledAlarmClock(RepeatingAlarm alarmClock);
}
