package com.mi.simple_alarm_clock_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static AlarmNotificationManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (alarmManager == null) {
            alarmManager = new AlarmNotificationManager(context);
        }

        if (intent.getAction().equals(Constants.ALARM_CLOCK_OFF_ACTION)) {
            alarmManager.notificationClose();
        }
        if (intent.getAction().equals(Constants.INTENT_TO_SHOW_ALARM_CLOCK_NOTIFICATION)) {
            alarmManager.showAlarmNotification();
        }
    }
}