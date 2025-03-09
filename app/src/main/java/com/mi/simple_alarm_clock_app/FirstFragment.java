package com.mi.simple_alarm_clock_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.mi.simple_alarm_clock_app.databinding.FragmentFirstBinding;

import java.util.Calendar;

public class FirstFragment extends Fragment implements MenuProvider {

    private FragmentFirstBinding binding;

    private NavController navController;

    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = requireContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.mtToolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        requireActivity().addMenuProvider(this, getViewLifecycleOwner());

        binding.btnAdd.setOnClickListener(btnAddView -> {

            MaterialTimePicker timePicker = Tools.getTimePickerFragment();

            timePicker.addOnPositiveButtonClickListener(tpView -> {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                AlarmClockManager manager = new AlarmClockManager(context);
                manager.setAlarmClock(Tools.getTimeInMillis(hour, minute));
            });

            timePicker.show(requireActivity().getSupportFragmentManager(), "time_picker");
        });
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.miSettings) {
            navigate(R.id.action_firstFragment_to_secondFragment);
        }
        return true;
    }

    private void navigate(int destination) {
        navController.navigate(destination);
    }
}