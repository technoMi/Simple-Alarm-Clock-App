package com.mi.simple_alarm_clock_app;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class PermissionTools {
    public static boolean getPermissionStatus(Context context, String permission) {
        int status = ContextCompat.checkSelfPermission(context, permission);
        return (status == PackageManager.PERMISSION_GRANTED);
    }

    public static void requestPermissions(
            FragmentActivity fragmentActivity,
            String notification,
            int requestCode) {
        String[] notifications = new String[] {notification};
        ActivityCompat.requestPermissions(fragmentActivity, notifications, requestCode);
    }

    public static void requestPermissions(
            FragmentActivity fragmentActivity,
            String[] notifications,
            int requestCode) {
        ActivityCompat.requestPermissions(fragmentActivity, notifications, requestCode);
    }
}
