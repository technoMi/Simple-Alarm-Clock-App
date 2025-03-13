package com.mi.simple_alarm_clock_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.security.Provider;

public class NotificationService extends Service {

    private final int NOTIFICATION_ID = 1;

    private final String CHANNEL_ID = "AlarmClockNotificationChannel";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startService();

        Intent intent1 = new Intent(this, AlarmClockActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent1);

        return START_STICKY;
    }

    private void startService() {
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
        startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "AlarmChannel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Alarm Clock")
                .setSmallIcon(R.drawable.baseline_alarm_24)
                .setSilent(false)
                .setAutoCancel(false);

        return builder;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
