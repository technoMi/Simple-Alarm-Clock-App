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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.mi.simple_alarm_clock_app.App;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmListBinding;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.ui.fragments.List.ListAdapter;

import java.util.ArrayList;

public class AlarmListFragment extends Fragment implements MenuProvider {

    private FragmentAlarmListBinding binding;

    private NavController navController;

    private Context context;

    private ActivityResultLauncher<String> requestPermissionResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = requireContext();

        requestPermissionResult = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        navigate(R.id.action_firstFragment_to_alarmEditFragment);
                    } else {
                        Tools.showToast(context, getString(R.string.permission_denied));
                    }
                });
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

        MaterialToolbar toolbar = view.findViewById(R.id.mtToolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        requireActivity().addMenuProvider(this, getViewLifecycleOwner());

        binding.btnAdd.setOnClickListener(btnAddView -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                String notificationPermission = Manifest.permission.POST_NOTIFICATIONS;
                boolean notificationGranted = Tools.getPermissionStatus(
                        context, notificationPermission
                );
                if (notificationGranted) {
                   navigate(R.id.action_firstFragment_to_alarmEditFragment);
                } else {
                    if (shouldShowRequestPermissionRationale(notificationPermission)) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                        Tools.showToast(context, getString(R.string.request_permission));
                    } else {
                        requestPermissionResult.launch(notificationPermission);
                    }
                }
            } else {
                navigate(R.id.action_firstFragment_to_alarmEditFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Alarm> alarms = new DatabaseManager().getAllAlarms();

        ListAdapter adapter = new ListAdapter(context, alarms);

        binding.rvAlarmList.setLayoutManager(new LinearLayoutManager(context));
        binding.rvAlarmList.setAdapter(adapter);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu, menu);
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
}