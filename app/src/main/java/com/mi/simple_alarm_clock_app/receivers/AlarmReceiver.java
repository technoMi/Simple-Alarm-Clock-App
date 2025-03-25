package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.ui.activities.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Actions.ALARM_ACTION)) {
            Intent alarmActivityIntent = new Intent(context, AlarmActivity.class);
            alarmActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alarmActivityIntent);
        }
    }
}
