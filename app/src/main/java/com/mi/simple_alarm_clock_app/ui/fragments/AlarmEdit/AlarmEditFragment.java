package com.mi.simple_alarm_clock_app.ui.fragments.AlarmEdit;

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
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.alarmclock.TimeUtils;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmEditBinding;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.AlarmTypes;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.Objects;

public class AlarmEditFragment extends Fragment {

    private final String TAG = "AlarmEditFragment";

    private FragmentAlarmEditBinding binding;

    private NavController navController;

    private Context context;

    private MaterialTimePicker timePicker;

    private MaterialDatePicker<Long> datePicker;

    private AlarmTypes typeOfScheduledAlarm;

    View.OnClickListener daysOfWeekCheckBoxesOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.tvAlarmDate.setText(getString(R.string.certain_time_tittle));
            typeOfScheduledAlarm = AlarmTypes.REPEATING;
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

        timePicker = Tools.getTimePickerFragment();
        datePicker = MaterialDatePicker.Builder.datePicker().build();

        binding.btnSetTime.setOnClickListener(stV -> {
            createTimePicker();
        });

        binding.btnSetDate.setOnClickListener(sdV -> {
            createDatePicker();
        });

        binding.btnCansel.setOnClickListener(v -> {
            navController.popBackStack();
        });

        binding.btnSave.setOnClickListener(v -> {
            saveAlarm();
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

    private void createTimePicker() {
        timePicker.show(requireActivity().getSupportFragmentManager(), "time_picker");

        timePicker.addOnPositiveButtonClickListener(pV -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            binding.tvTime.setText(Tools.getFormattedTimeTittle(hour, minute));
        });
    }

    private void createDatePicker() {
        datePicker.show(requireActivity().getSupportFragmentManager(), "date_picker");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            typeOfScheduledAlarm = AlarmTypes.SINGLE;

            binding.tvAlarmDate.setText(Tools.getDateTittle(datePicker.getSelection()));

            clearDaysOfWeekCheckBoxes();
        });
    }

    private void saveAlarm() {
        boolean someDaysOfWeekSelected = (
                binding.cbMonday.isChecked() || binding.cbTuesday.isChecked() ||
                        binding.cbWednesday.isChecked() || binding.cbThursday.isChecked() ||
                        binding.cbFriday.isChecked() || binding.cbSaturday.isChecked() ||
                        binding.cbSunday.isChecked()
        );

        boolean dateSelected;
        try {
            dateSelected = datePicker.getSelection() != 0;
        } catch (NullPointerException e) {
            dateSelected = false;
        }

        boolean isAlarmValidate = (someDaysOfWeekSelected || dateSelected);

        if (isAlarmValidate) {

            int id = DatabaseManager.getNewAlarmEntityItemID(typeOfScheduledAlarm);

            String name = String.valueOf(binding.etAlarmName.getText());

            long dateTimeInMillis;

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            long alarmTime;
            boolean isEnabled = true;


            if (typeOfScheduledAlarm.equals(AlarmTypes.SINGLE)) {

                dateTimeInMillis = datePicker.getSelection();

                alarmTime = TimeUtils.getAlarmTimeInMillis(dateTimeInMillis, hour, minute);

                SingleAlarm singleAlarm = new SingleAlarm(
                        id,
                        name,
                        alarmTime,
                        isEnabled
                );

                new AlarmClockManager(context).setAlarmClockInSystemManager(singleAlarm);

                new DatabaseManager().saveAlarm(singleAlarm);
            }
            if (typeOfScheduledAlarm.equals(AlarmTypes.REPEATING)) {

                boolean mondayChecked = binding.cbMonday.isChecked();
                boolean tuesdayChecked = binding.cbTuesday.isChecked();
                boolean wednesdayChecked = binding.cbWednesday.isChecked();
                boolean thursdayChecked = binding.cbThursday.isChecked();
                boolean fridayChecked = binding.cbFriday.isChecked();
                boolean saturdayChecked = binding.cbSaturday.isChecked();
                boolean sundayChecked = binding.cbSunday.isChecked();

                RepeatingAlarm repeatingAlarm = new RepeatingAlarm(
                        id,
                        name,
                        hour,
                        minute,
                        isEnabled,
                        mondayChecked,
                        tuesdayChecked,
                        wednesdayChecked,
                        thursdayChecked,
                        fridayChecked,
                        saturdayChecked,
                        sundayChecked
                );

                new AlarmClockManager(context).setAlarmClockInSystemManager(repeatingAlarm);

                new DatabaseManager().saveAlarm(repeatingAlarm);
            }

            navController.popBackStack();
        } else {
            Tools.showToast(context, getString(R.string.invalid_form));
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