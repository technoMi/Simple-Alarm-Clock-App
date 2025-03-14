package com.mi.simple_alarm_clock_app;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

public class AlarmSoundPlayer {

    private MediaPlayer mediaPlayer;

    public AlarmSoundPlayer(Context context) {
        mediaPlayer = MediaPlayer.create(context, getSoundUri());
    }

    private Uri getSoundUri() {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        if (soundUri != null) {
            soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }

        return soundUri;
    }

    public void playSound() {
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void stopSound() {
        mediaPlayer.stop();
    }
}
