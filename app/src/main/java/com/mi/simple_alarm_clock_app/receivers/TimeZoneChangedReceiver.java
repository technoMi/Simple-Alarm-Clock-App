package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.ui.activities.MainActivity;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimeZoneChangedReceiver extends BroadcastReceiver {

    private final String TAG = "TimeZoneChangedReceiver";

    private CompositeDisposable compositeDisposable;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {

            disableAlarms(context);

            Intent appIntent = new Intent(context, MainActivity.class);
            appIntent.putExtra("time_zone_changed", true);
            appIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(appIntent);
        }
    }

    private void disableAlarms(Context context) {
        DatabaseManager dbManager = new DatabaseManager();
        AlarmClockManager acManager = new AlarmClockManager(context);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                dbManager.getAllAlarms()
                        .flatMap(alarms -> Observable.fromIterable(alarms)
                                .doOnNext(alarm -> {
                                    alarm.setEnabled(false);
                                    dbManager.updateAlarm(alarm);
                                    acManager.canselAlarmInSystemManager(alarm);
                                }).toList())
                        .doOnTerminate(() -> compositeDisposable.clear())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                alarms -> {
                                    Log.i(TAG, alarms.size() + " disabled");
                                }, throwable -> {
                                    Log.w(TAG, throwable.getCause());
                                }
                        )
        );
    }
}
