package com.mi.simple_alarm_clock_app.ui.fragments.AlarmEdit;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmEditBinding;

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

        viewModel = new ViewModelProvider(
                this, new EditFragmentViewModelFactory(requireContext())).get(EditFragmentViewModel.class);

        viewModel.dateTimeInMillis.observe(getViewLifecycleOwner(), time -> {
            binding.tvAlarmDate.setText(Tools.getDateTittleFromMillis(time));
        });

        viewModel.timeInMillis.observe(getViewLifecycleOwner(), time -> {
            binding.tvTime.setText(Tools.getFormattedTimeTittleFromMillis(time));
        });

        viewModel.isValidate.observe(getViewLifecycleOwner(), isValidate -> {
            if (!isValidate) Tools.showToast(context, getString(R.string.invalid_form));
        });

        viewModel.alarmClockCreated.observe(getViewLifecycleOwner(), created -> {
            if (created) navController.popBackStack();
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
            String name = String.valueOf(binding.etAlarmName.getText());
            saveAlarm(name);
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
            viewModel.setTimeInMillis(timePicker.getHour(), timePicker.getMinute());
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

    private void saveAlarm(String name) {
        viewModel.setAlarm(name);
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