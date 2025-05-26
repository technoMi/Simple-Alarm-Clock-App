package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.Collections;
import java.util.List;

public class SingleAlarmRepository implements AlarmRepository<SingleAlarm> {

    private final SingleAlarmDao dao;

    public SingleAlarmRepository(SingleAlarmDao dao) {
        this.dao = dao;
    }

    @Override
    public List<SingleAlarm> getAllAlarms() {
        return dao.getAllSingleAlarmClocks();
    }

    @Override
    public void insertNewAlarm(SingleAlarm alarm) {
        dao.insertNewScheduledSingleAlarmClock(alarm);
    }

    @Override
    public void deleteAlarm(SingleAlarm alarm) {
        dao.deleteScheduledSingleAlarmClock(alarm);
    }

    @Override
    public SingleAlarm getAlarmByID(int id) {
        return dao.getItemById(id);
    }

    @Override
    public void doesItemExistById(int id) {
        dao.doesItemExistById(id);
    }

    @Override
    public void updateAlarm(SingleAlarm alarm) {
        dao.updateScheduledAlarmClock(alarm);
    }
}
