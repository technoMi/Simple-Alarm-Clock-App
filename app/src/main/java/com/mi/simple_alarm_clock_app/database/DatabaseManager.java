package com.mi.simple_alarm_clock_app.database;

import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.AlarmType;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

public class DatabaseManager {

    public Single<ArrayList<Alarm>> getAllAlarms() {

        return Single.create(new SingleOnSubscribe<ArrayList<Alarm>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<ArrayList<Alarm>> emitter) throws Throwable {
                SingleAlarmDao singleAlarmDao = App.getInstance().getSingleAlarmDao();
                RepeatingAlarmDao repeatingAlarmDao = App.getInstance().getRepeatingAlarmDao();

                ArrayList<Alarm> allAlarmsList = new ArrayList<>();
                ArrayList<SingleAlarm> singleAlarmList = (ArrayList<SingleAlarm>) singleAlarmDao.getAllSingleAlarmClocks();
                ArrayList<RepeatingAlarm> repeatingAlarmList = (ArrayList<RepeatingAlarm>) repeatingAlarmDao.getAllRepeatingAlarmClocks();

                allAlarmsList.addAll(singleAlarmList);
                allAlarmsList.addAll(repeatingAlarmList);

                emitter.onSuccess(allAlarmsList);
            }
        });
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

    public void saveAlarm(Alarm alarm) {
        if (alarm instanceof SingleAlarm) {
            saveSingleAlarm((SingleAlarm) alarm);
        }
        if (alarm instanceof RepeatingAlarm) {
            saveRepeatingAlarm((RepeatingAlarm) alarm);
        }
    }

    private void saveSingleAlarm(SingleAlarm newItem) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
                alarmDao.insertNewScheduledSingleAlarmClock(newItem);
            }
        }.start();
    }

    private void saveRepeatingAlarm(RepeatingAlarm newItem) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
                alarmDao.insertNewScheduledRepeatingAlarmClock(newItem);
            }
        }.start();
    }

    public void updateAlarm(Alarm alarm) {
        if (alarm instanceof SingleAlarm) {
            updateSingleAlarmClock((SingleAlarm) alarm);
        }
        if (alarm instanceof RepeatingAlarm) {
            updateRepeatingAlarmClock((RepeatingAlarm) alarm);
        }
    }

    private void updateSingleAlarmClock(SingleAlarm item) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
                alarmDao.updateScheduledAlarmClock(item);
            }
        }.start();
    }

    private void updateRepeatingAlarmClock(RepeatingAlarm item) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
                alarmDao.updateScheduledAlarmClock(item);
            }
        }.start();
    }

    public void deleteAlarm(Alarm alarm) {
        if (alarm instanceof SingleAlarm) {
            deleteSingleAlarm((SingleAlarm) alarm);
        }
        if (alarm instanceof RepeatingAlarm) {
            deleteRepeatingAlarm((RepeatingAlarm) alarm);
        }
    }

    private void deleteSingleAlarm(SingleAlarm item) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
                alarmDao.deleteScheduledSingleAlarmClock(item);
            }
        }.start();
    }

    private void deleteRepeatingAlarm(RepeatingAlarm item) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
                alarmDao.deleteScheduledRepeatingAlarmClock(item);
            }
        }.start();
    }


    public static int getNewAlarmEntityItemID(AlarmType type) {

        if (type.equals(AlarmType.SINGLE)) {
            return getNewSingleAlarmItemID();
        }
        if (type.equals(AlarmType.REPEATING)) {
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
        } while (alarmDao.doesItemExistById(newId));

        return newId;
    }

    private static int getNewRepeatingAlarmItemID() {
        RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
        int newId;

        // todo добавить асинхронность
        do {
            newId = new Random().nextInt();
        } while (alarmDao.doesItemExistById(newId));

        return newId;
    }
}
