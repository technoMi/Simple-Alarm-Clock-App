package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.AlarmType;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;
import com.mi.simple_alarm_clock_app.ui.activities.AlarmActivity;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlarmReceiver extends BroadcastReceiver {

    private final String TAG = "AlarmReceiver";

    private CompositeDisposable compositeDisposable;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Actions.ALARM_ACTION)) {

            int alarmId = intent.getIntExtra("id", -1);

            Disposable dispose = new DatabaseManager().getAlarmById(alarmId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            alarm -> {
                                determineAlarmRelevance(alarm, context);
                            }, throwable -> {
                                Log.w(TAG, throwable.getCause());
                            }
                    );

            compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(dispose);

            Intent alarmActivityIntent = new Intent(context, AlarmActivity.class);
            alarmActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alarmActivityIntent);

            Log.i(TAG, TAG + " onReceive. ID: " + alarmId);
        }
    }

    private void determineAlarmRelevance(Alarm alarm, Context context) {
        AlarmManager acManager = new AlarmManager(context);
        DatabaseManager dbManager = new DatabaseManager();

        compositeDisposable.add(
                Completable.fromAction(() -> {
                            if (alarm instanceof SingleAlarm) {
                                dbManager.deleteAlarm(alarm);
                            } else {
                                acManager.recalculateTimeForAlarmClock(alarm);
                                dbManager.updateAlarm(alarm);
                            }
                        })
                        .doOnTerminate(() -> compositeDisposable.clear())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    Log.i(TAG, "Successfully update of alarm in the database");
                                }, throwable -> {
                                    Log.w(TAG, throwable.getCause());
                                }
                        )
        );
    }
}