package com.mi.simple_alarm_clock_app.alarmclock;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlarmRebootHandler {

    private static final String TAG = "AlarmRebootHandler";

    public static void resetAlarmsAfterReboot(Context context) {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AlarmClockManager acManager = new AlarmClockManager(context);
        DatabaseManager dbManager = new DatabaseManager();

        compositeDisposable.add(dbManager.getAllAlarms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(compositeDisposable::clear)
                .subscribe(
                        alarms -> {
                            for (Alarm alarm : alarms) {
                                acManager.setAlarmInSystemManager(alarm);
                            }
                        }, throwable -> {
                            Log.w(TAG, throwable.getCause());
                        }
                )
        );
    }
}
