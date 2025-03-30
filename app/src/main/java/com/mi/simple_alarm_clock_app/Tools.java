package com.mi.simple_alarm_clock_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.TimeZone;

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

    public static long getTimeInMillis(long dateInMillis, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(dateInMillis);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static String getFormattedTimeForAlarmClock(String time) {
        String formattedTime;
        if (time.equals("0")) {
            formattedTime = "00";
        } else if (time.length() == 1) {
            formattedTime = "0" + time;
        } else {
            formattedTime = time;
        }
        return formattedTime;
    }

    public static boolean getPermissionStatus(Context context, String permission) {
        int status = ContextCompat.checkSelfPermission(context, permission);
        return (status == PackageManager.PERMISSION_GRANTED);
    }
}
