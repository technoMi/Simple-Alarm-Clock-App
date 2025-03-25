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
import android.widget.CheckBox;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.alarmclock.AlarmManager;
import com.mi.simple_alarm_clock_app.alarmclock.TimeInfoForAlarm;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmEditBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

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

            long alarmTime = Tools.getTimeInMillis(dayTimeInMillis, hour, minute);

            AlarmManager manager = new AlarmManager(context);

            int id = DatabaseManager.getNewItemID();

            ArrayList<String> daysOfWeek = new ArrayList<>();
            if (binding.cbMonday.isChecked()) daysOfWeek.add("MON");
            if (binding.cbTuesday.isChecked()) daysOfWeek.add("TUE");
            if (binding.cbWednesday.isChecked()) daysOfWeek.add("WED");
            if (binding.cbThursday.isChecked()) daysOfWeek.add("THU");
            if (binding.cbFriday.isChecked()) daysOfWeek.add("FRI");
            if (binding.cbSaturday.isChecked()) daysOfWeek.add("SAT");
            if (binding.cbSunday.isChecked()) daysOfWeek.add("SUN");

            Bundle alarmParameters = new Bundle();
            alarmParameters.putInt("id", id);
            alarmParameters.putStringArrayList("daysOfWeek", daysOfWeek);

            manager.setAlarmClock(alarmParameters, alarmTime);

            navController.popBackStack();
        });

        super.onViewCreated(view, savedInstanceState);
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