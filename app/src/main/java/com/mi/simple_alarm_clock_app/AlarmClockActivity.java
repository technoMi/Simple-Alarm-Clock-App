package com.mi.simple_alarm_clock_app;

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

    private Ringtone ringtone;

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

        if (ringtonUri == null) {
            ringtonUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }

        if (ringtonUri != null) {
            ringtone = RingtoneManager.getRingtone(getApplicationContext(), ringtonUri);
            ringtone.play();
        }
    }

    private void stopAlarmSound() {
        ringtone.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ringtone = null;
    }
}