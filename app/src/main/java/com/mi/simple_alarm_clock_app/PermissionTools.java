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
}
