package com.mi.simple_alarm_clock_app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmRebootHandler;

public class BootReceiver extends BroadcastReceiver {

    private final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    "AppPreferences", Context.MODE_PRIVATE
            );

            boolean appDidNotStartAfterReboot = sharedPreferences.getBoolean(
                    "run_after_reboot", true
            );

            if (appDidNotStartAfterReboot) {
                AlarmRebootHandler.resetAlarmsAfterReboot(context);
            }
        }
    }
}
