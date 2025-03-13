package com.mi.simple_alarm_clock_app;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mi.simple_alarm_clock_app.databinding.ActivityAlarmClockBinding;

public class AlarmClockActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private ActivityAlarmClockBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAlarmClockBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnCloseAlarm.setOnClickListener(v -> {
            stopAlarmSound();
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        playAlarmSound();
    }

    private void playAlarmSound() {

        Uri ringtonUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        mediaPlayer = MediaPlayer.create(this, ringtonUri);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void stopAlarmSound() {
        mediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}