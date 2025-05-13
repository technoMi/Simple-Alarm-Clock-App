package com.mi.simple_alarm_clock_app.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmListBinding;
import com.mi.simple_alarm_clock_app.ui.fragments.List.ListAdapter;
import com.mi.simple_alarm_clock_app.ui.fragments.List.ListViewModel;

public class AlarmListFragment extends Fragment implements MenuProvider {

    private FragmentAlarmListBinding binding;

    private NavController navController;

    private Context context;

    private ListViewModel viewModel;

    private ListAdapter listAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = requireContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAlarmListBinding.inflate(inflater, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        MaterialToolbar toolbar = view.findViewById(R.id.mtToolbar);
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        requireActivity().addMenuProvider(this, getViewLifecycleOwner());

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        checkExtras();

        viewModel.liveAlarms.observe(getViewLifecycleOwner(), alarms -> {
            listAdapter = new ListAdapter(context, alarms);
            binding.rvAlarmList.setAdapter(listAdapter);
        });

        binding.rvAlarmList.setLayoutManager(new LinearLayoutManager(context));

        binding.btnAdd.setOnClickListener(btnAddView -> {
            navigate(R.id.action_firstFragment_to_alarmEditFragment);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getAllAlarmsFromDatabase();
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.fragment_list_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.miSettings) {
            navigate(R.id.action_firstFragment_to_alarmEditFragment);
        }
        return true;
    }

    private void navigate(int destination) {
        navController.navigate(destination);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (listAdapter != null) {
            listAdapter.clearCompositeDisposable();
        }
    }

    private void checkExtras() {
        Intent intent = requireActivity().getIntent();
        if (intent.getBooleanExtra("time_zone_changed", false)) {
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context)
                    .setTitle(getString(R.string.attention))
                    .setMessage(getString(R.string.time_zone_changed))
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                        // nothing
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            intent.removeExtra("time_zone_changed");
        }
    }
}