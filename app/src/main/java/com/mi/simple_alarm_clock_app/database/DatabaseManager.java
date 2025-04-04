package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.model.Alarm;
import java.util.Random;

public class DatabaseManager {
    public static int getNewItemID() {
        ScheduledAlarmDao alarmDao = App.getInstance().getScheduledAlarmClockDao();
        int newId;

        // todo добавить асинхронность
        do {
            newId = new Random().nextInt();
        } while(alarmDao.doesItemExistById(newId));

        newId = new Random().nextInt();

        return newId;
    }

    public Alarm getAlarmClockById(int id) {
        // todo добавить асинхронность
        ScheduledAlarmDao alarmDao = App.getInstance().getScheduledAlarmClockDao();
        return alarmDao.getItemById(id);
    }

    public void saveAlarmClock(Alarm newItem) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ScheduledAlarmDao alarmDao = App.getInstance().getScheduledAlarmClockDao();
                alarmDao.insertNewScheduledAlarmClock(newItem);
            }
        }.start();
    }

    public void updateAlarmClock(Alarm item) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ScheduledAlarmDao alarmDao = App.getInstance().getScheduledAlarmClockDao();
                alarmDao.updateScheduledAlarmClock(item);
            }
        }.start();
    }

    public void deleteAlarmClock(Alarm item) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ScheduledAlarmDao alarmDao = App.getInstance().getScheduledAlarmClockDao();
                alarmDao.deleteScheduledAlarmClock(item);
            }
        }.start();
    }
}
