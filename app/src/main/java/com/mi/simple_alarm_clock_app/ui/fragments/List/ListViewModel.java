package com.mi.simple_alarm_clock_app.ui.fragments.List;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    private String TAG = "ListViewModel";

    private CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<ArrayList<Alarm>> mutableAlarms = new MutableLiveData<>();

    public LiveData<ArrayList<Alarm>> liveAlarms = mutableAlarms;

    public void getAllAlarmsFromDatabase() {
        Disposable dispose = new DatabaseManager().getAllAlarms()
                        .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                list -> {
                                                    mutableAlarms.setValue(list);
                                                }, throwable -> {
                                                    Log.w(TAG, throwable.toString());
                                                }
                                        );

        disposables.add(dispose);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
