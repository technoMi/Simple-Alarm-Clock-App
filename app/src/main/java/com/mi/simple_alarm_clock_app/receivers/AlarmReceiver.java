package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.ui.activities.AlarmActivity;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Actions.ALARM_ACTION)) {

            int alarmId = intent.getIntExtra("id", -1);

            Alarm alarm = getAlarmClockFromDbById(alarmId);

            boolean allow = getLaunchPermission(alarm);

            if (allow) {
                Intent alarmActivityIntent = new Intent(context, AlarmActivity.class);
                alarmActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(alarmActivityIntent);

                determineAlarmCLockRelevance(alarm, context);
            }
        }
    }

    private boolean getLaunchPermission(Alarm a) {

        boolean notSingle = (a.isMonday() || a.isTuesday() || a.isWednesday() || a.isThursday() ||
                a.isFriday() || a.isSaturday() || a.isSunday());

        if (notSingle) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(a.getDateTimeInMillis());
            int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeekNumber == 1 && a.isSunday()) return true;
            if (dayOfWeekNumber == 2 && a.isMonday()) return true;
            if (dayOfWeekNumber == 3 && a.isTuesday()) return true;
            if (dayOfWeekNumber == 4 && a.isWednesday()) return true;
            if (dayOfWeekNumber == 5 && a.isThursday()) return true;
            if (dayOfWeekNumber == 6 && a.isFriday()) return true;
            if (dayOfWeekNumber == 7 && a.isSaturday()) return true;

        } else {
            // For single alarm
            return true;
        }
        return false;
    }

    private Alarm getAlarmClockFromDbById(int id) {
        DatabaseManager dbManager = new DatabaseManager();
        return dbManager.getAlarmClockById(id);
    }

    private void determineAlarmCLockRelevance(Alarm a, Context context) {

        boolean notSingle = (a.isMonday() || a.isTuesday() || a.isWednesday() || a.isThursday() ||
                a.isFriday() || a.isSaturday() || a.isSunday());

        if (notSingle) {
            AlarmClockManager acManager = new AlarmClockManager(context);
            acManager.recalculateTimeForAlarmClock(a);
        } else {
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.deleteAlarmClock(a);
        }
    }
}
