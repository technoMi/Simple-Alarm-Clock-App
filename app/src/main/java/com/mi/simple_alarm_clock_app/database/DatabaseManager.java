package com.mi.simple_alarm_clock_app.database;

import androidx.room.Entity;

import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseManager {

    public ArrayList<Alarm> getAllAlarms() {
        // todo добавить асинхронность
        SingleAlarmDao singleAlarmDao = App.getInstance().getSingleAlarmDao();
        RepeatingAlarmDao repeatingAlarm = App.getInstance().getRepeatingAlarmDao();

        ArrayList<Alarm> allAlarmsList = new ArrayList<>();
        ArrayList<Alarm> singleAlarmList = (ArrayList<Alarm>) singleAlarmDao.getAllSingleAlarmClocks();
        ArrayList<Alarm> repeatingAlarmList = (ArrayList<Alarm>) repeatingAlarm.getAllRepeatingAlarmClocks();

        allAlarmsList.addAll(singleAlarmList);
        allAlarmsList.addAll(repeatingAlarmList);

        return allAlarmsList;
    }


    public SingleAlarm getSingleAlarmById(int id) {
        // todo добавить асинхронность
        SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
        return alarmDao.getItemById(id);
    }

    public RepeatingAlarm getRepeatingAlarmById(int id) {
        // todo добавить асинхронность
        RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
        return alarmDao.getItemById(id);
    }


    public void saveSingleAlarmClock(SingleAlarm newItem) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
                alarmDao.insertNewScheduledSingleAlarmClock(newItem);
            }
        }.start();
    }

    public void saveRepeatingAlarmClock(RepeatingAlarm newItem) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
                alarmDao.insertNewScheduledRepeatingAlarmClock(newItem);
            }
        }.start();
    }


    public void updateSingleAlarmClock(SingleAlarm item) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
                alarmDao.updateScheduledAlarmClock(item);
            }
        }.start();
    }

    public void updateRepeatingAlarmClock(RepeatingAlarm item) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
                alarmDao.updateScheduledAlarmClock(item);
            }
        }.start();
    }


    public void deleteSingleAlarmClock(SingleAlarm item) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
                alarmDao.deleteScheduledSingleAlarmClock(item);
            }
        }.start();
    }


    public static int getNewAlarmEntityItemID(Entity entity) {

        if (entity instanceof SingleAlarm) {
            return getNewSingleAlarmItemID();
        }
        if (entity instanceof RepeatingAlarm) {
            return getNewRepeatingAlarmItemID();
        }

        return 0;
    }

    private static int getNewSingleAlarmItemID() {
        SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
        int newId;

        // todo добавить асинхронность
        do {
            newId = new Random().nextInt();
        } while(alarmDao.doesItemExistById(newId));

        return newId;
    }

    private static int getNewRepeatingAlarmItemID() {
        RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
        int newId;

        // todo добавить асинхронность
        do {
            newId = new Random().nextInt();
        } while(alarmDao.doesItemExistById(newId));

        return newId;
    }
}
