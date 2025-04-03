package com.mi.simple_alarm_clock_app.ui.fragments.AlarmEdit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.alarmclock.TimeInfoForAlarm;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmEditBinding;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.AlarmValidator;

public class AlarmEditFragment extends Fragment {

    private final String TAG = "AlarmEditFragment";

    private FragmentAlarmEditBinding binding;

    private NavController navController;

    private Context context;

    private TimeInfoForAlarm timeInfoForAlarm;

    View.OnClickListener daysOfWeekCheckBoxesOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (timeInfoForAlarm == null) {
                timeInfoForAlarm = new TimeInfoForAlarm();
            }
            binding.tvAlarmDate.setText(getString(R.string.certain_time_tittle));
            timeInfoForAlarm.setSelectedDayInMillis(0);
        }
    };

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

        Bundle alarmBundle = getArguments();
        if (alarmBundle != null) {
            setInViewsInformationFromBundle(alarmBundle);
        }

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

                clearDaysOfWeekCheckBoxes();

                updateAlarmInfoOnTheScreen();
            });

        });

        binding.btnCansel.setOnClickListener(v -> {
            navController.popBackStack();
        });

        binding.btnSave.setOnClickListener(v -> {
            long dateTimeInMillis;
            try {
                dateTimeInMillis = timeInfoForAlarm.getSelectedDayInMillis();
            } catch (NullPointerException e) {
                dateTimeInMillis = 0;
            }

            int hour;
            int minute;
            try {
                hour = timeInfoForAlarm.getHour();
                minute = timeInfoForAlarm.getMinute();
            } catch (NullPointerException e) {
                hour = -1;
                minute = -1;
            }

            String name = String.valueOf(binding.etAlarmName.getText());

            boolean mondayChecked = binding.cbMonday.isChecked();
            boolean tuesdayChecked = binding.cbTuesday.isChecked();
            boolean wednesdayChecked = binding.cbWednesday.isChecked();
            boolean thursdayChecked = binding.cbThursday.isChecked();
            boolean fridayChecked = binding.cbFriday.isChecked();
            boolean saturdayChecked = binding.cbSaturday.isChecked();
            boolean sundayChecked = binding.cbSunday.isChecked();

            boolean enabled = true;

            long time = Tools.getTimeInMillis(dateTimeInMillis, hour, minute);
            int id = DatabaseManager.getNewItemID();

            Alarm newAlarm = new Alarm(
                    id,
                    name,
                    time,
                    enabled,
                    mondayChecked,
                    tuesdayChecked,
                    wednesdayChecked,
                    thursdayChecked,
                    fridayChecked,
                    saturdayChecked,
                    sundayChecked
            );

            if (AlarmValidator.isValidate(newAlarm)) {
                AlarmClockManager alarmManager = new AlarmClockManager(context);
                alarmManager.setAlarmClockInSystemManager(newAlarm);

                DatabaseManager dbManager = new DatabaseManager();
                dbManager.saveAlarmClock(newAlarm);

                navController.popBackStack();
            } else {
                Tools.showToast(context, getString(R.string.invalid_form));
            }
        });

        binding.cbMonday.setOnClickListener(daysOfWeekCheckBoxesOnClickListener);
        binding.cbTuesday.setOnClickListener(daysOfWeekCheckBoxesOnClickListener);
        binding.cbWednesday.setOnClickListener(daysOfWeekCheckBoxesOnClickListener);
        binding.cbThursday.setOnClickListener(daysOfWeekCheckBoxesOnClickListener);
        binding.cbFriday.setOnClickListener(daysOfWeekCheckBoxesOnClickListener);
        binding.cbSaturday.setOnClickListener(daysOfWeekCheckBoxesOnClickListener);
        binding.cbSunday.setOnClickListener(daysOfWeekCheckBoxesOnClickListener);

        super.onViewCreated(view, savedInstanceState);
    }

    private void setInViewsInformationFromBundle(Bundle alarmBundle) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                int id = alarmBundle.getInt("id");
                Alarm alarm = App.getInstance().getScheduledAlarmClockDao().getAllAlarmClocks().get(id);

                new Handler(Looper.getMainLooper()).post(() -> {

                });
            }
        }.start();
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
                binding.tvAlarmDate.setText(timeInfoForAlarm.getDateTittle());
            }
        }
    }

    private void clearDaysOfWeekCheckBoxes() {
        binding.cbMonday.setChecked(false);
        binding.cbTuesday.setChecked(false);
        binding.cbWednesday.setChecked(false);
        binding.cbThursday.setChecked(false);
        binding.cbFriday.setChecked(false);
        binding.cbSaturday.setChecked(false);
        binding.cbSunday.setChecked(false);
    }
}