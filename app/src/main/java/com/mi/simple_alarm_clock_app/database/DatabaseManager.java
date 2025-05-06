package com.mi.simple_alarm_clock_app.database;

import android.util.Log;

import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.AlarmType;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DatabaseManager {

    public Single<ArrayList<Alarm>> getAllAlarms() {
        return Single.create(emitter -> {
            SingleAlarmDao singleAlarmDao = App.getInstance().getSingleAlarmDao();
            RepeatingAlarmDao repeatingAlarmDao = App.getInstance().getRepeatingAlarmDao();

            ArrayList<Alarm> allAlarmsList = new ArrayList<>();
            ArrayList<SingleAlarm> singleAlarmList = (ArrayList<SingleAlarm>) singleAlarmDao.getAllSingleAlarmClocks();
            ArrayList<RepeatingAlarm> repeatingAlarmList = (ArrayList<RepeatingAlarm>) repeatingAlarmDao.getAllRepeatingAlarmClocks();

            allAlarmsList.addAll(singleAlarmList);
            allAlarmsList.addAll(repeatingAlarmList);

            emitter.onSuccess(allAlarmsList);
        });
    }


    public Single<Alarm> getAlarmById(int id) {
        return Single.create(emitter -> {
            Alarm alarm = getRepeatingAlarmById(id);
            if (alarm == null) {
                alarm = getSingleAlarmById(id);
            }
            emitter.onSuccess(alarm);
        });
    }

    private SingleAlarm getSingleAlarmById(int id) {
        SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
        return alarmDao.getItemById(id);
    }

    private RepeatingAlarm getRepeatingAlarmById(int id) {
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
        SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
        alarmDao.insertNewScheduledSingleAlarmClock(newItem);
    }

    private void saveRepeatingAlarm(RepeatingAlarm newItem) {
        RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
        alarmDao.insertNewScheduledRepeatingAlarmClock(newItem);
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
        SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
        alarmDao.updateScheduledAlarmClock(item);
    }

    private void updateRepeatingAlarmClock(RepeatingAlarm item) {
        RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
        alarmDao.updateScheduledAlarmClock(item);
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
        SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
        alarmDao.deleteScheduledSingleAlarmClock(item);
    }

    private void deleteRepeatingAlarm(RepeatingAlarm item) {
        RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
        alarmDao.deleteScheduledRepeatingAlarmClock(item);
    }


    public int getNewAlarmEntityItemID(AlarmType type) {

        if (type.equals(AlarmType.SINGLE)) {
            return getNewSingleAlarmItemID();
        }
        if (type.equals(AlarmType.REPEATING)) {
            return getNewRepeatingAlarmItemID();
        }

        return 0;
    }

    private int getNewSingleAlarmItemID() {
        SingleAlarmDao alarmDao = App.getInstance().getSingleAlarmDao();
        int newId;

//        // todo добавить асинхронность
//        do {
            newId = new Random().nextInt();
//        } while (alarmDao.doesItemExistById(newId));

        return newId;
    }

    private int getNewRepeatingAlarmItemID() {
        RepeatingAlarmDao alarmDao = App.getInstance().getRepeatingAlarmDao();
        int newId;

        // todo добавить асинхронность
//        do {
            newId = new Random().nextInt();
//        } while (alarmDao.doesItemExistById(newId));

        return newId;
    }
}
