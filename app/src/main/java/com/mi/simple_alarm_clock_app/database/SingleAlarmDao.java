package com.mi.simple_alarm_clock_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.List;

@Dao
public interface SingleAlarmDao {

    @Query("SELECT * FROM single_alarms ORDER BY name DESC")
    List<Alarm> getAllSingleAlarmClocks();

    @Insert(entity = SingleAlarm.class)
    void insertNewScheduledSingleAlarmClock(Alarm alarmClock);

    @Delete(entity = SingleAlarm.class)
    void deleteScheduledSingleAlarmClock(Alarm alarmClock);

    @Query("SELECT * FROM single_alarms WHERE id == :id")
    SingleAlarm getItemById(int id);

    @Query("SELECT EXISTS(SELECT 1 FROM single_alarms WHERE id == :id)")
    boolean doesItemExistById(int id);

    @Update(entity = SingleAlarm.class)
    void updateScheduledAlarmClock(SingleAlarm alarmClock);
}
