package com.mi.simple_alarm_clock_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmNotificationManager {

    private Context context;

    private final String ALARM_CLOCK_CHANNEL_ID = "alarm_clock_channel_id";

    private final int NOTIFICATION_ID = 1;

    private NotificationManagerCompat notificationManager;

    private Ringtone ringtone;

    public AlarmNotificationManager(Context context) {
        this.context = context;
    }

    public void showAlarmNotification() {
        createNotificationChannel();
        createNotification();
        playSound();
    }

    private void playSound() {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        if (soundUri == null) {
            soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }

        ringtone = RingtoneManager.getRingtone(context, soundUri);

        if (ringtone != null) {
            ringtone.play();
        }
    }

    private void createNotification() {

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(Constants.ALARM_CLOCK_OFF_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ALARM_CLOCK_CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_alarm_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle("Будильник")
                .addAction(R.drawable.baseline_alarm_24, "Отключить", pendingIntent)
                .setAutoCancel(false)
                .setSilent(true);

        notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void notificationClose() {
        if (notificationManager == null) {
            notificationManager = NotificationManagerCompat.from(context);
        }
        notificationManager.cancel(NOTIFICATION_ID);
        ringtone.stop();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelName = "AlarmClockChannel";

            NotificationChannel channel = new NotificationChannel(
                    ALARM_CLOCK_CHANNEL_ID,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
