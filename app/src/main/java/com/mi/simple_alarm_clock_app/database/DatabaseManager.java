package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.App;

import java.util.Random;

public class DatabaseManager {
    public static int getNewItemID() {
        ScheduledAlarmDao alarmDao = App.getInstance().getScheduledAlarmClockDao();
        int newId;

        // todo необходима проверка на существование ключа
//        do {
//            newId = new Random().nextInt();
//        } while(!alarmDao.doesItemExistById(newId));

        newId = new Random().nextInt();

        return newId;
    }
}
