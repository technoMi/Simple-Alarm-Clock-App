package com.mi.simple_alarm_clock_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

//    public static String getHourAndMinuteTittleFromMillis(long time) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(time);
//
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//
//        String formattedHour = (hour < 10) ? ("0" + hour) : String.valueOf(hour);
//        String formattedMinute = (minute < 10) ? ("0" + minute) : String.valueOf(minute);
//
//        return formattedHour + ":" + formattedMinute;
//    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static String getFormattedTittleFromHourAndMinute(int hour, int minute) {
        String formattedHour = getFormattedTimeForAlarmClock(String.valueOf(hour));
        String formattedMinute = getFormattedTimeForAlarmClock(String.valueOf(minute));
        return formattedHour + ":" + formattedMinute;
    }

    private static String getFormattedTimeForAlarmClock(String time) {
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
