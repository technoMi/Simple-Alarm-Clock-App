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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlarmReceiver extends BroadcastReceiver {

    private final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Actions.ALARM_ACTION)) {

            int alarmId = intent.getIntExtra("id", -1);

            // todo что делать с dispose?
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

            Intent alarmActivityIntent = new Intent(context, AlarmActivity.class);
            alarmActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alarmActivityIntent);

            Log.i(TAG, TAG + " onReceive. ID: " + alarmId);
        }
    }

    private void determineAlarmRelevance(Alarm alarm, Context context) {
        AlarmManager acManager = new AlarmManager(context);
        DatabaseManager dbManager = new DatabaseManager();

        if (alarm instanceof SingleAlarm) {
            // todo что делать с dispose?
            Disposable dispose = Single.create(emitter -> {
                        dbManager.deleteAlarm(alarm);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            success -> {
                                Log.i(TAG, "Successful update of alarm in the database");
                            }, throwable -> {
                                Log.w(TAG, throwable.getCause());
                            }
                    );
        } else {
            acManager.recalculateTimeForAlarmClock(alarm);

            // todo что делать с dispose?
            Disposable dispose = Single.create(emitter -> {
                        dbManager.updateAlarm(alarm);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            success -> {
                                Log.i(TAG, "Successful update of alarm in the database");
                            }, throwable -> {
                                Log.w(TAG, throwable.getCause());
                            }
                    );
        }
    }
}
