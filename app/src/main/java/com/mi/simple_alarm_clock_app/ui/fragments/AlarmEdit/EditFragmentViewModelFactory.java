package com.mi.simple_alarm_clock_app.ui.fragments.AlarmEdit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;

public class EditFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public EditFragmentViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditFragmentViewModel(new AlarmManager(context), new DatabaseManager());
    }
}
