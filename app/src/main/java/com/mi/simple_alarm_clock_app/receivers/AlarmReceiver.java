package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.AlarmTypes;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;
import com.mi.simple_alarm_clock_app.ui.activities.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private final String TAG = "Debug.AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Actions.ALARM_ACTION)) {

            int alarmId = intent.getIntExtra("id", -1);
            String alarmType = intent.getStringExtra("type");

            Alarm alarm = getAlarmFromDbById(alarmId, alarmType);

            Intent alarmActivityIntent = new Intent(context, AlarmActivity.class);
            alarmActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alarmActivityIntent);

            Log.d(TAG, TAG + " onReceive. ID: " + alarmId);

            determineAlarmRelevance(alarm, context);
        }
    }

    private Alarm getAlarmFromDbById(int id, String alarmType) {
        DatabaseManager dbManager = new DatabaseManager();
        if (alarmType.equals(AlarmTypes.SINGLE.toString())) {
            return dbManager.getSingleAlarmById(id);
        } else {
            return dbManager.getRepeatingAlarmById(id);
        }
    }

    private void determineAlarmRelevance(Alarm alarm, Context context) {
        AlarmClockManager acManager = new AlarmClockManager(context);
        DatabaseManager dbManager = new DatabaseManager();

        if (alarm instanceof SingleAlarm) {
            dbManager.deleteAlarm(alarm);
        } else {
            acManager.recalculateTimeForAlarmClock(alarm);
            dbManager.updateAlarm(alarm);
        }
    }
}
