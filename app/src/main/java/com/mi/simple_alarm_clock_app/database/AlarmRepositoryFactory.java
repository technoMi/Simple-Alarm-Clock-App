package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.HashMap;
import java.util.Map;

public class AlarmRepositoryFactory {
    private final Map<Class<? extends Alarm>, AlarmRepository<? extends Alarm>> repositoryMap = Map.of(
            SingleAlarm.class, new SingleAlarmRepository(SingleAlarmDao),
            RepeatingAlarm.class, new RepeatingAlarmRepository(RepeatingAlarmDao)
    );


}
