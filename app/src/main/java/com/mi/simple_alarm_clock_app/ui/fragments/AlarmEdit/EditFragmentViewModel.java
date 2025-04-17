package com.mi.simple_alarm_clock_app.ui.fragments.AlarmEdit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmManager;
import com.mi.simple_alarm_clock_app.alarmclock.TimeUtils;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.AlarmType;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

public class EditFragmentViewModel extends ViewModel {

    private AlarmManager alarmManager;
    private DatabaseManager dbManager;

    private AlarmType alarmType;

    private final MutableLiveData<Boolean> isValidateMutable = new MutableLiveData<>();
    public LiveData<Boolean> isValidate = isValidateMutable;

    private final MutableLiveData<Boolean> alarmClockCreatedMutable = new MutableLiveData<>();
    public LiveData<Boolean> alarmClockCreated = alarmClockCreatedMutable;

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

    public EditFragmentViewModel(AlarmManager alarmManager, DatabaseManager databaseManager) {
        this.alarmManager = alarmManager;
        this.dbManager = databaseManager;
    }

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

       // Set and case of processing when flags are cleared
       if (isMonday || isTuesday || isWednesday || isThursday || isFriday || isSaturday || isSunday) {
           setAlarmType(AlarmType.REPEATING);
       } else {
           setAlarmType(null);
       }
    }

    public void setDateTimeInMillis(long dateTimeInMillis) {
        this.mutableDateTimeInMillis.setValue(dateTimeInMillis);
        setAlarmType(AlarmType.SINGLE);
    }

    public void setTimeInMillis(int hour, int minute) {
        long time = ((long) hour * 60 * 60 * 1000) + ((long) minute * 60 * 1000);
        mutableTimeInMillis.setValue(time);
    }

    private void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public void setAlarm() {
        boolean isValidate = isAlarmValidate();

        if (!isValidate) {
            isValidateMutable.setValue(false);
        } else {
            saveAlarm();
        }
    }

    private boolean isAlarmValidate() {
        return (mutableTimeInMillis.getValue() != null) && (alarmType != null);
    }

    private void saveAlarm() {
        int id = new DatabaseManager().getNewAlarmEntityItemID(alarmType);

        if (alarmType == AlarmType.SINGLE) {
            long timeInMillis = mutableDateTimeInMillis.getValue() + mutableTimeInMillis.getValue();

            SingleAlarm alarm = new SingleAlarm(
                    id,
                    "",
                    timeInMillis,
                    true
            );
            saveAlarmInSystemManager(alarm);
            saveAlarmInDatabase(alarm);
        }

        if (alarmType == AlarmType.REPEATING) {
            int hour = TimeUtils.getHourFromMillis(mutableTimeInMillis.getValue());
            int minute = TimeUtils.getMinuteFromMillis(mutableTimeInMillis.getValue());

            RepeatingAlarm alarm = new RepeatingAlarm(
                    id,
                    "",
                    hour,
                    minute,
                    true,
                    isMonday,
                    isTuesday,
                    isWednesday,
                    isThursday,
                    isFriday,
                    isSaturday,
                    isSunday
            );
            saveAlarmInSystemManager(alarm);
            saveAlarmInDatabase(alarm);
        }

        alarmClockCreatedMutable.setValue(true);
    }

    private void saveAlarmInSystemManager(Alarm alarm) {
        alarmManager.setAlarmClockInSystemManager(alarm);
    }

    private void saveAlarmInDatabase(Alarm alarm) {
        dbManager.saveAlarm(alarm);
    }
}
