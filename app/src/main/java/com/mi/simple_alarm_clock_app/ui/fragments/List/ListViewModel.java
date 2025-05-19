package com.mi.simple_alarm_clock_app.ui.fragments.List;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
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

    private final ArrayList<RecyclerView.ViewHolder> holders = new ArrayList<>();

    public ListViewModel(AlarmClockManager alarmClockManager, DatabaseManager databaseManager) {
        this.alarmClockManager = alarmClockManager;
        this.databaseManager = databaseManager;
    }

    public void clearListOfAlarmsInActionMode() {
        ArrayList<Alarm> list = mutableAlarmsInActionMode.getValue();
        if (list != null) {
            list.clear();
            mutableAlarmsInActionMode.setValue(list);
        }
    }

    public void addItemToSelectedItemsInActionMode(Alarm alarm, RecyclerView.ViewHolder holder) {
        ArrayList<Alarm> updatedList = mutableAlarmsInActionMode.getValue();
        updatedList.add(alarm);
        holders.add(holder);
        mutableAlarmsInActionMode.setValue(updatedList);
    }

    public void removeAlarmFromSelectedItemsInActionMode(Alarm alarm, RecyclerView.ViewHolder holder) {
        ArrayList<Alarm> list = mutableAlarmsInActionMode.getValue();
        list.remove(alarm);
        mutableAlarmsInActionMode.setValue(list);
        holders.remove(holder);
    }

    public ArrayList<RecyclerView.ViewHolder> getHoldersOfSelectedItems() {
        return holders;
    }

    public void deleteSelectedAlarmsInActionMode() {
        //todo убрать disposable
        Disposable disposable = Observable.create(emitter -> {
                    ArrayList<Alarm> list = mutableAlarmsInActionMode.getValue();
                    for (Alarm alarm : list) {
                        new DatabaseManager().deleteAlarm(alarm);
                        emitter.onNext(alarm);
                    }
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(disposables::clear)
                .subscribe(
                        alarm -> {
                            alarmClockManager.canselAlarmInSystemManager((Alarm) alarm);
                            removeAlarmFromSelectedItemsInActionMode((Alarm) alarm, null);
                        }, throwable -> {
                            Log.w(TAG, throwable.getCause());
                        }, () -> {
                            holders.clear();
                            getAllAlarmsFromDatabase();
                            Log.i(TAG, "Successfully deleting of alarm");
                        }
                );
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
