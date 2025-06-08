package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;

public class RepeatingAlarmStrategy implements AlarmStrategy<RepeatingAlarm> {

    private final RepeatingAlarmDao dao = App.getInstance().getRepeatingAlarmDao();

    @Override
    public AlarmDao<RepeatingAlarm> getDao() {
        return dao;
    }
}
