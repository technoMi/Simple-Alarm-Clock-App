package com.mi.simple_alarm_clock_app.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mi.simple_alarm_clock_app.alarmclock.AlarmSoundPlayer;
import com.mi.simple_alarm_clock_app.databinding.ActivityAlarmClockBinding;

public class AlarmActivity extends AppCompatActivity {

    private ActivityAlarmClockBinding binding;

    private AlarmSoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAlarmClockBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        soundPlayer = new AlarmSoundPlayer(this);

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
        soundPlayer.playSound();
    }

    private void stopAlarmSound() {
        soundPlayer.stopSound();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPlayer.stopSound();
        soundPlayer = null;
    }
}