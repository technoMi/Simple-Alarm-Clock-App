package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;

import java.util.Map;

public class AlarmDaoRepository {

    private static final Map<Class<?>, AlarmStrategy<?>> alarmStrategies = Map.of(
                RepeatingAlarm.class, new RepeatingAlarmStrategy()
            );

    public static AlarmStrategy<?> getDaoFor(Alarm alarm) {
        return alarmStrategies.get(alarm.getClass());
    }

}