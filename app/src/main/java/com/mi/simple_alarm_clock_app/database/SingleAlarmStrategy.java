package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

public class SingleAlarmStrategy implements AlarmStrategy<SingleAlarm> {

    private final SingleAlarmDao dao = App.getInstance().getSingleAlarmDao();

    @Override
    public AlarmDao<SingleAlarm> getDao() {
        return null;
    }
}
