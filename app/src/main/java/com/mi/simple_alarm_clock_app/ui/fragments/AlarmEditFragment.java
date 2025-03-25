package com.mi.simple_alarm_clock_app.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.mi.simple_alarm_clock_app.Constants;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.alarmclock.TimeInfoForAlarm;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmEditBinding;

import java.util.ArrayList;

public class AlarmEditFragment extends Fragment {

    private FragmentAlarmEditBinding binding;

    private NavController navController;

    private Context context;

    private TimeInfoForAlarm timeInfoForAlarm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAlarmEditBinding.inflate(getLayoutInflater());

        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        context = requireContext();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.btnSetTime.setOnClickListener(stV -> {
            MaterialTimePicker timePicker = Tools.getTimePickerFragment();

            timePicker.show(requireActivity().getSupportFragmentManager(), "time_picker");

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

            datePicker.show(requireActivity().getSupportFragmentManager(), "date_picker");

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
            long dayTimeInMillis = timeInfoForAlarm.getSelectedDayInMillis();
            int hour = timeInfoForAlarm.getHour();
            int minute = timeInfoForAlarm.getMinute();
            String name = String.valueOf(binding.etAlarmName.getText());

            ArrayList<String> daysOfWeek = getCheckedDaysOfWeek();

            AlarmClockManager manager = new AlarmClockManager(context);
            manager.saveAlarmClock(name, dayTimeInMillis, hour, minute, daysOfWeek);

            navController.popBackStack();
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private ArrayList<String> getCheckedDaysOfWeek() {
        ArrayList<String> checkedDaysOfWeek = new ArrayList<>();

        if (binding.cbMonday.isChecked()) checkedDaysOfWeek.add(Constants.SHORT_MONDAY_TITTLE);
        if (binding.cbTuesday.isChecked()) checkedDaysOfWeek.add(Constants.SHORT_TUESDAY_TITTLE);
        if (binding.cbWednesday.isChecked()) checkedDaysOfWeek.add(Constants.SHORT_WEDNESDAY_TITTLE);
        if (binding.cbThursday.isChecked()) checkedDaysOfWeek.add(Constants.SHORT_THURSDAY_TITTLE);
        if (binding.cbFriday.isChecked()) checkedDaysOfWeek.add(Constants.SHORT_FRIDAY_TITTLE);
        if (binding.cbSaturday.isChecked()) checkedDaysOfWeek.add(Constants.SHORT_SATURDAY_TITTLE);
        if (binding.cbSunday.isChecked()) checkedDaysOfWeek.add(Constants.SHORT_SUNDAY_TITTLE);

        return checkedDaysOfWeek;
    }

    @SuppressLint("SetTextI18n")
    private void updateAlarmInfoOnTheScreen() {
        if (timeInfoForAlarm != null) {
            if (timeInfoForAlarm.getHour() != -1 && timeInfoForAlarm.getMinute() != -1) {
                String hour = String.valueOf(timeInfoForAlarm.getHour());
                String formattedHour = Tools.getFormattedTimeForAlarmClock(hour);

                String minute = String.valueOf(timeInfoForAlarm.getMinute());
                String formattedMinute = Tools.getFormattedTimeForAlarmClock(minute);

                binding.tvTime.setText(formattedHour + ":" + formattedMinute);
            }
            if (timeInfoForAlarm.getDateTittle() != null) {
                binding.alarmDate.setText(timeInfoForAlarm.getDateTittle());
            }
        }
    }
}