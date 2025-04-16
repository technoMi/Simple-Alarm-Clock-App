package com.mi.simple_alarm_clock_app.ui.fragments.AlarmEdit;

import androidx.collection.ObjectListKt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mi.simple_alarm_clock_app.alarmclock.TimeUtils;
import com.mi.simple_alarm_clock_app.model.AlarmType;

import java.util.Calendar;

public class EditFragmentViewModel extends ViewModel {

    private AlarmType alarmType;

    private final MutableLiveData<Boolean> isValidateMutable = new MutableLiveData<>();
    public LiveData<Boolean> isValidate = isValidateMutable;

    private final MutableLiveData<Long> mutableDateTimeInMillis = new MutableLiveData<>();
    public LiveData<Long> dateTimeInMillis = mutableDateTimeInMillis;

    private final MutableLiveData<Long> mutableTimeInMillis = new MutableLiveData<>();
    public LiveData<Long> timeInMillis = mutableTimeInMillis;

    private boolean isMonday;
    private boolean isTuesday;
    private boolean isWednesday;
    private boolean isThursday;
    private boolean isFriday;
    private boolean isSaturday;
    private boolean isSunday;

    public void setDaysOfWeek(
        boolean isMonday,
        boolean isTuesday,
        boolean isWednesday,
        boolean isThursday,
        boolean isFriday,
        boolean isSaturday,
        boolean isSunday
    ) {
       this.isMonday = isMonday;
       this.isTuesday = isTuesday;
       this.isWednesday = isWednesday;
       this.isThursday = isThursday;
       this.isFriday = isFriday;
       this.isSaturday = isSaturday;
       this.isSunday = isSunday;

       setAlarmType(AlarmType.REPEATING);
    }

    public void setDateTimeInMillis(long dateTimeInMillis) {
        this.mutableDateTimeInMillis.setValue(dateTimeInMillis);
    }

    public void setTime(int hour, int minute) {
        long time = ((long) hour * 60 * 60 * 1000) + ((long) minute * 60 * 1000);
        mutableTimeInMillis.setValue(time);
    }

    private void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public void saveAlarm() {
        boolean isValidate = isAlarmValidate();

        if (!isValidate) {
            isValidateMutable.setValue(isValidate);
        } else {

        }
    }

    private boolean isAlarmValidate() {
        return mutableTimeInMillis.getValue() != null && alarmType != null;
                //(mutableDateTimeInMillis.getValue() != null || oneDayOfWeekWasChosen());
    }

    private boolean oneDayOfWeekWasChosen() {
        return (
                isMonday || isTuesday || isWednesday || isThursday || isFriday || isSaturday
                || isSunday
        );
    }
}
