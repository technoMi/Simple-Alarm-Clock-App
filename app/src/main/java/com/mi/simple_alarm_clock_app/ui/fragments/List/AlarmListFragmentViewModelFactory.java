package com.mi.simple_alarm_clock_app.ui.fragments.List;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;

public class AlarmListFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public AlarmListFragmentViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListViewModel(new AlarmClockManager(context), new DatabaseManager());
    }
}
