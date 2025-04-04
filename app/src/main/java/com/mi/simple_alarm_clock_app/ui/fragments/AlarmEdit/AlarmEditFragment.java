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
import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmEditBinding;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.AlarmValidator;

public class AlarmEditFragment extends Fragment {

    private final String TAG = "AlarmEditFragment";

    private FragmentAlarmEditBinding binding;

    private NavController navController;

    private Context context;

    private Alarm scheduledAlarm;

    View.OnClickListener daysOfWeekCheckBoxesOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.tvAlarmDate.setText(getString(R.string.certain_time_tittle));
            scheduledAlarm.setDateTimeInMillis(0);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduledAlarm = new Alarm();
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

        Bundle alarmBundle = getArguments();
        if (alarmBundle != null) {
            setInViewsInformationFromBundle(alarmBundle);
            initScheduledAlarmFromBundle(alarmBundle);
        }

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

    private void setInViewsInformationFromBundle(Bundle alarmBundle) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                int id = alarmBundle.getInt("id");
                DatabaseManager manager = new DatabaseManager();
                Alarm alarm = manager.getAlarmClockById(id);

                new Handler(Looper.getMainLooper()).post(() -> {

                    scheduledAlarm.setName(alarm.getName());

                    int hour = alarm.getHour();
                    int minute = alarm.getMinute();

                    scheduledAlarm.setHour(hour);
                    scheduledAlarm.setMinute(minute);
                    scheduledAlarm.setDateTimeInMillis(alarm.getDateTimeInMillis());

                    scheduledAlarm.setMonday(alarm.isMonday());
                    scheduledAlarm.setTuesday(alarm.isTuesday());
                    scheduledAlarm.setWednesday(alarm.isWednesday());
                    scheduledAlarm.setThursday(alarm.isThursday());
                    scheduledAlarm.setFriday(alarm.isFriday());
                    scheduledAlarm.setSaturday(alarm.isSaturday());
                    scheduledAlarm.setSunday(alarm.isSunday());

                    binding.tvTime.setText(Tools.getFormattedTittleFromHourAndMinute(hour, minute));

                    binding.etAlarmName.setText(alarm.getName());

                    binding.cbMonday.setChecked(alarm.isMonday());
                    binding.cbTuesday.setChecked(alarm.isTuesday());
                    binding.cbWednesday.setChecked(alarm.isWednesday());
                    binding.cbThursday.setChecked(alarm.isThursday());
                    binding.cbFriday.setChecked(alarm.isFriday());
                    binding.cbSaturday.setChecked(alarm.isSaturday());
                    binding.cbSunday.setChecked(alarm.isSunday());
                });
            }
        }.start();
    }

    private void initScheduledAlarmFromBundle(Bundle alarmBundle) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                int id = alarmBundle.getInt("id");
                Alarm alarm = App.getInstance().getScheduledAlarmClockDao().getItemById(id);

                new Handler(Looper.getMainLooper()).post(() -> {

                    scheduledAlarm.setId(id);

                    scheduledAlarm.setName(alarm.getName());

                    scheduledAlarm.setHour(alarm.getHour());
                    scheduledAlarm.setMinute(alarm.getMinute());
                    scheduledAlarm.setDateTimeInMillis(alarm.getDateTimeInMillis());

                    scheduledAlarm.setMonday(alarm.isMonday());
                    scheduledAlarm.setTuesday(alarm.isTuesday());
                    scheduledAlarm.setWednesday(alarm.isWednesday());
                    scheduledAlarm.setThursday(alarm.isThursday());
                    scheduledAlarm.setFriday(alarm.isFriday());
                    scheduledAlarm.setSaturday(alarm.isSaturday());
                    scheduledAlarm.setSunday(alarm.isSunday());
                });
            }
        }.start();
    }

    private void createTimePicker() {
        MaterialTimePicker timePicker = Tools.getTimePickerFragment();

        timePicker.show(requireActivity().getSupportFragmentManager(), "time_picker");

        timePicker.addOnPositiveButtonClickListener(pV -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            scheduledAlarm.setHour(hour);
            scheduledAlarm.setMinute(minute);

            binding.tvTime.setText(Tools.getFormattedTittleFromHourAndMinute(hour, minute));
        });
    }

    private void createDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();

        datePicker.show(requireActivity().getSupportFragmentManager(), "date_picker");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            scheduledAlarm.setDateTimeInMillis(selection);

            binding.tvAlarmDate.setText(datePicker.getHeaderText());

            clearDaysOfWeekCheckBoxes();
        });
    }

    private void saveAlarm() {
        long dateTimeInMillis;

        dateTimeInMillis = scheduledAlarm.getDateTimeInMillis();
        if (dateTimeInMillis == 0) {
            dateTimeInMillis = Tools.getTodayDateTimeInMillis();
        }

        int hour;
        int minute;
        try {
            hour = scheduledAlarm.getHour();
            minute = scheduledAlarm.getMinute();
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

        if (getArguments() == null) {
            int id = DatabaseManager.getNewItemID();
            scheduledAlarm.setId(id);
        }

        scheduledAlarm.setName(name);
        scheduledAlarm.setEnabled(true);

        scheduledAlarm.setHour(hour);
        scheduledAlarm.setMinute(minute);
        scheduledAlarm.setDateTimeInMillis(dateTimeInMillis);

        scheduledAlarm.setMonday(mondayChecked);
        scheduledAlarm.setTuesday(tuesdayChecked);
        scheduledAlarm.setWednesday(wednesdayChecked);
        scheduledAlarm.setThursday(thursdayChecked);
        scheduledAlarm.setFriday(fridayChecked);
        scheduledAlarm.setSaturday(saturdayChecked);
        scheduledAlarm.setSunday(sundayChecked);

        if (AlarmValidator.isValidate(scheduledAlarm)) {
            AlarmClockManager alarmManager = new AlarmClockManager(context);
            DatabaseManager dbManager = new DatabaseManager();

            if (getArguments() != null) {
                alarmManager.canselAlarmClockInSystemManager(scheduledAlarm);
            }

            alarmManager.setAlarmClockInSystemManager(scheduledAlarm);

            if (getArguments() != null) {
                dbManager.updateAlarmClock(scheduledAlarm);
            } else {
                dbManager.saveAlarmClock(scheduledAlarm);
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