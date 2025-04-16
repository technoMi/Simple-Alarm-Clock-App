package com.mi.simple_alarm_clock_app.ui.fragments.AlarmEdit;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import com.mi.simple_alarm_clock_app.model.AlarmType;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.sql.Time;

public class AlarmEditFragment extends Fragment {

    private final String TAG = "AlarmEditFragment";

    private FragmentAlarmEditBinding binding;

    private NavController navController;

    private Context context;

    private EditFragmentViewModel viewModel;

    View.OnClickListener daysOfWeekCheckBoxesOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewModel.setDaysOfWeek(
                    binding.cbMonday.isChecked(),
                    binding.cbTuesday.isChecked(),
                    binding.cbWednesday.isChecked(),
                    binding.cbThursday.isChecked(),
                    binding.cbFriday.isChecked(),
                    binding.cbSaturday.isChecked(),
                    binding.cbSunday.isChecked()
            );
            binding.tvAlarmDate.setText(R.string.certain_time_tittle);
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

        viewModel = new ViewModelProvider(this).get(EditFragmentViewModel.class);

        viewModel.dateTimeInMillis.observe(getViewLifecycleOwner(), time -> {
            binding.tvAlarmDate.setText(Tools.getDateTittleFromMillis(time));
        });

        viewModel.timeInMillis.observe(getViewLifecycleOwner(), time -> {
            binding.tvTime.setText(Tools.getFormattedTimeTittleFromMillis(time));
        });

        viewModel.isValidate.observe(getViewLifecycleOwner(), isValidate -> {
            if (!isValidate) Tools.showToast(context, getString(R.string.invalid_form));
        });

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
        MaterialTimePicker timePicker = Tools.getTimePickerFragment();

        timePicker.show(requireActivity().getSupportFragmentManager(), "time_picker");

        timePicker.addOnPositiveButtonClickListener(pV -> {
            viewModel.setTime(timePicker.getHour(), timePicker.getMinute());
        });
    }

    private void createDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();

        datePicker.show(requireActivity().getSupportFragmentManager(), "date_picker");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            viewModel.setDateTimeInMillis(selection);
            clearDaysOfWeekCheckBoxes();
        });
    }

    private void saveAlarm() {
        viewModel.saveAlarm();
//        boolean someDaysOfWeekSelected = (
//                binding.cbMonday.isChecked() || binding.cbTuesday.isChecked() ||
//                        binding.cbWednesday.isChecked() || binding.cbThursday.isChecked() ||
//                        binding.cbFriday.isChecked() || binding.cbSaturday.isChecked() ||
//                        binding.cbSunday.isChecked()
//        );
//
//        boolean dateSelected;
//        try {
//            dateSelected = datePicker.getSelection() != 0;
//        } catch (NullPointerException e) {
//            dateSelected = false;
//        }
//
//        boolean isAlarmValidate = (someDaysOfWeekSelected || dateSelected);
//
//        if (isAlarmValidate) {
//
//            int id = DatabaseManager.getNewAlarmEntityItemID(typeOfScheduledAlarm);
//
//            String name = String.valueOf(binding.etAlarmName.getText());
//
//            long dateTimeInMillis;
//
//            int hour = timePicker.getHour();
//            int minute = timePicker.getMinute();
//
//            long alarmTime;
//            boolean isEnabled = true;
//
//
//            if (typeOfScheduledAlarm.equals(AlarmType.SINGLE)) {
//
//                dateTimeInMillis = datePicker.getSelection();
//
//                alarmTime = TimeUtils.getAlarmTimeInMillis(dateTimeInMillis, hour, minute);
//
//                SingleAlarm singleAlarm = new SingleAlarm(
//                        id,
//                        name,
//                        alarmTime,
//                        isEnabled
//                );
//
//                new AlarmClockManager(context).setAlarmClockInSystemManager(singleAlarm);
//
//                new DatabaseManager().saveAlarm(singleAlarm);
//            }
//            if (typeOfScheduledAlarm.equals(AlarmType.REPEATING)) {
//
//                boolean mondayChecked = binding.cbMonday.isChecked();
//                boolean tuesdayChecked = binding.cbTuesday.isChecked();
//                boolean wednesdayChecked = binding.cbWednesday.isChecked();
//                boolean thursdayChecked = binding.cbThursday.isChecked();
//                boolean fridayChecked = binding.cbFriday.isChecked();
//                boolean saturdayChecked = binding.cbSaturday.isChecked();
//                boolean sundayChecked = binding.cbSunday.isChecked();
//
//                RepeatingAlarm repeatingAlarm = new RepeatingAlarm(
//                        id,
//                        name,
//                        hour,
//                        minute,
//                        isEnabled,
//                        mondayChecked,
//                        tuesdayChecked,
//                        wednesdayChecked,
//                        thursdayChecked,
//                        fridayChecked,
//                        saturdayChecked,
//                        sundayChecked
//                );
//
//                new AlarmClockManager(context).setAlarmClockInSystemManager(repeatingAlarm);
//
//                new DatabaseManager().saveAlarm(repeatingAlarm);
//            }
//
//            navController.popBackStack();
//        } else {
//            Tools.showToast(context, getString(R.string.invalid_form));
//        }
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