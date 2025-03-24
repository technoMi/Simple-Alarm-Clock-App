package com.mi.simple_alarm_clock_app.ui.fragments;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.alarmclock.AlarmManager;
import com.mi.simple_alarm_clock_app.alarmclock.TimeInfoForAlarm;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmEditBinding;

import java.util.Calendar;
import java.util.TimeZone;

public class AlarmEditFragment extends Fragment {

    private FragmentAlarmEditBinding binding;

    private NavController navController;

    private Context context;

    private FragmentActivity fragmentActivity;

    private TimeInfoForAlarm timeInfoForAlarm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

                if (timeInfoForAlarm == null) {
                    timeInfoForAlarm = new TimeInfoForAlarm();
                }
                timeInfoForAlarm.setHour(timePicker.getHour());
                timeInfoForAlarm.setMinute(timePicker.getMinute());

                updateAlarmInfoOnTheScreen();
            });
        });

        binding.btnSetDate.setOnClickListener(sdV -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();

            datePicker.show(fragmentActivity.getSupportFragmentManager(), "date_picker");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                if (timeInfoForAlarm == null) {
                    timeInfoForAlarm = new TimeInfoForAlarm();
                }
                timeInfoForAlarm.setSelectedDayInMillis(selection);
                timeInfoForAlarm.setDateTittle(datePicker.getHeaderText());

                updateAlarmInfoOnTheScreen();
            });

        });

        binding.btnCansel.setOnClickListener(v -> {
            navController.popBackStack();
        });

        binding.btnSave.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(timeInfoForAlarm.getSelectedDayInMillis());

            calendar.set(Calendar.HOUR_OF_DAY, timeInfoForAlarm.getHour());
            calendar.set(Calendar.MINUTE, timeInfoForAlarm.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            AlarmManager manager = new AlarmManager(context);



            manager.setAlarmClock(calendar.getTimeInMillis());

            Toast.makeText(context, String.valueOf(calendar.getTimeInMillis()), Toast.LENGTH_LONG).show();

            navController.popBackStack();
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    private void updateAlarmInfoOnTheScreen() {
        if (timeInfoForAlarm != null) {
            if (timeInfoForAlarm.getHour() != -1 && timeInfoForAlarm.getMinute() != -1) {
                String hour = String.valueOf(timeInfoForAlarm.getHour());

                String minute = String.valueOf(timeInfoForAlarm.getMinute());

                String formattedMinute = (minute.equals("0") ? "00" : minute);

                binding.tvTime.setText(hour + ":" + formattedMinute);
            }
            if (timeInfoForAlarm.getDateTittle() != null) {
                binding.alarmDate.setText(timeInfoForAlarm.getDateTittle());
            }
        }
    }
}