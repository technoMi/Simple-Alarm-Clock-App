package com.mi.simple_alarm_clock_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mi.simple_alarm_clock_app.databinding.ActivityAlarmClockBinding;

public class AlarmClockActivity extends AppCompatActivity {

    private ActivityAlarmClockBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAlarmClockBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnCloseAlarm.setOnClickListener(v -> {
            finish();
        });
    }
}