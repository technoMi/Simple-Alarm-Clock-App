package com.mi.simple_alarm_clock_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class Tools {

    public static MaterialTimePicker getTimePickerFragment() {
        return new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("")
                .setHour(0)
                .setMinute(0)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build();
    }

    public static long getTimeInMillis(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTimeInMillis();
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static boolean getPermissionStatus(Context context, String permission) {
        int status = ContextCompat.checkSelfPermission(context, permission);
        return (status == PackageManager.PERMISSION_GRANTED);
    }
}
