package com.mi.simple_alarm_clock_app.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmEditBinding;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class AlarmEditFragment extends Fragment {

    private FragmentAlarmEditBinding binding;

    private NavController navController;

    private Context context;

    private FragmentActivity fragmentActivity;

    private int hour;
    private int minute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAlarmEditBinding.inflate(getLayoutInflater());

        fragmentActivity = requireActivity();

        navController = Navigation.findNavController(fragmentActivity, R.id.fragmentContainerView);

        context = requireContext();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.btnSetTime.setOnClickListener(stV -> {
            MaterialTimePicker timePicker = Tools.getTimePickerFragment();

            timePicker.show(fragmentActivity.getSupportFragmentManager(), "time_picker");

            timePicker.addOnPositiveButtonClickListener(pV -> {
                this.hour = timePicker.getHour();
                this.minute = timePicker.getMinute();
                updateAlarmInfoOnTheScreen();
            });
        });

        binding.btnSetDate.setOnClickListener(sdV -> {
            MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().build();

            datePicker.show(fragmentActivity.getSupportFragmentManager(), "date_picker");

            datePicker.addOnPositiveButtonClickListener(pV -> {

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(datePicker.getSelection());


            });
        });

        binding.btnCansel.setOnClickListener(v -> {
            navController.popBackStack();
        });

        binding.btnSave.setOnClickListener(v -> {

        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void updateAlarmInfoOnTheScreen() {
        binding.tvTime.setText(hour + ":" + minute);
    }
}