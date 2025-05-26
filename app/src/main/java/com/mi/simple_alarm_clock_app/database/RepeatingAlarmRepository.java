package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;

import java.util.Collections;
import java.util.List;

public class RepeatingAlarmRepository implements AlarmRepository<RepeatingAlarm> {

    private final RepeatingAlarmDao dao;

    public RepeatingAlarmRepository(RepeatingAlarmDao dao) {
        this.dao = dao;
    }

    @Override
    public List<RepeatingAlarm> getAllAlarms() {
        return dao.getAllRepeatingAlarmClocks();
    }

    @Override
    public void insertNewAlarm(RepeatingAlarm alarm) {
        dao.insertNewScheduledRepeatingAlarmClock(alarm);
    }

    @Override
    public void deleteAlarm(RepeatingAlarm alarm) {
        dao.deleteScheduledRepeatingAlarmClock(alarm);
    }

    @Override
    public RepeatingAlarm getAlarmByID(int id) {
        return dao.getItemById(id);
    }

    @Override
    public void doesItemExistById(int id) {
        dao.doesItemExistById(id);
    }

    @Override
    public void updateAlarm(RepeatingAlarm alarm) {
        dao.updateScheduledAlarmClock(alarm);
    }
}
