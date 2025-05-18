package com.mi.simple_alarm_clock_app.ui.fragments.List;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    private String TAG = "ListViewModel";

    private final AlarmClockManager alarmClockManager;

    private final DatabaseManager databaseManager;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<ArrayList<Alarm>> mutableAlarms = new MutableLiveData<>(new ArrayList<>());
    public LiveData<ArrayList<Alarm>> liveAlarms = mutableAlarms;

    private final MutableLiveData<ArrayList<Alarm>> mutableAlarmsInActionMode = new MutableLiveData<>(new ArrayList<>());
    public LiveData<ArrayList<Alarm>> liveAlarmsInActionMode = mutableAlarmsInActionMode;

    public ListViewModel(AlarmClockManager alarmClockManager, DatabaseManager databaseManager) {
        this.alarmClockManager = alarmClockManager;
        this.databaseManager = databaseManager;
    }

    public void addItemToSelectedItemsInActionMode(Alarm alarm) {
        ArrayList<Alarm> updatedList = mutableAlarmsInActionMode.getValue();
        updatedList.add(alarm);
        mutableAlarmsInActionMode.setValue(updatedList);
    }

    public void removeAlarmFromSelectedItemsInActionMode(Alarm alarm) {
        ArrayList<Alarm> list = mutableAlarmsInActionMode.getValue();
        list.remove(alarm);
        mutableAlarmsInActionMode.setValue(list);
    }

    public void getAllAlarmsFromDatabase() {
        disposables.add(new DatabaseManager().getAllAlarms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(disposables::clear)
                .subscribe(
                        alarms -> {
                            mutableAlarms.setValue(alarms);
                        }, throwable -> {
                            Log.w(TAG, throwable.getCause());
                        }
                )
        );
    }

    public void updateAlarmState(Alarm alarm, boolean isChecked) {
        alarm.setEnabled(!alarm.isEnabled());
        if (isChecked) {
            alarm.doBeforeAlarmTurnedOn();
            alarmClockManager.setAlarmInSystemManager(alarm);
        } else {
           alarmClockManager.canselAlarmInSystemManager(alarm);
        }

        disposables.add(Completable.fromAction(() -> {
                    databaseManager.updateAlarm(alarm);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(disposables::clear)
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
