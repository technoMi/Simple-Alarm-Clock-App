package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mi.simple_alarm_clock_app.AlarmClockActivity;
import com.mi.simple_alarm_clock_app.Constants;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Constants.INTENT_TO_SHOW_ALARM_CLOCK_NOTIFICATION)) {
//            Intent serviceIntent = new Intent(context, NotificationService.class);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(serviceIntent);
//            } else {
//                context.startService(serviceIntent);
//            }

            Intent intent1 = new Intent(context, AlarmClockActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}