package com.mi.simple_alarm_clock_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.mi.simple_alarm_clock_app.databinding.FragmentFirstBinding;
import com.mi.simple_alarm_clock_app.entities.MyTimePicker;

public class FirstFragment extends Fragment implements MenuProvider {

    private FragmentFirstBinding binding;

    private NavController navController;

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

            MyTimePicker timePicker = new MyTimePicker();

            timePicker.getTimePickerFragment().addOnPositiveButtonClickListener(tpView -> {
                Toast.makeText(requireContext(), String.valueOf(timePicker.getHour()), Toast.LENGTH_LONG).show();
            });

            timePicker.getTimePickerFragment().show(requireActivity().getSupportFragmentManager(), "time_picker");
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