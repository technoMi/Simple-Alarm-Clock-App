package com.mi.simple_alarm_clock_app.ui.fragments.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Alarm>> mutableAlarms = new MutableLiveData<>();

    public LiveData<ArrayList<Alarm>> liveAlarms = mutableAlarms;


    public void getAllAlarmsFromDatabase() {
        mutableAlarms.setValue(new DatabaseManager().getAllAlarms());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
