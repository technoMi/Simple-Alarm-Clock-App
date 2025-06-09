package com.mi.simple_alarm_clock_app.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmListBinding;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.ui.fragments.List.AlarmListFragmentViewModelFactory;
import com.mi.simple_alarm_clock_app.ui.fragments.List.ListAdapter;
import com.mi.simple_alarm_clock_app.ui.fragments.List.ListViewModel;

import java.util.List;

public class AlarmListFragment extends Fragment implements MenuProvider, ListAdapter.ListAdapterListener {

    private final String TAG = "AlarmListFragment";

    private FragmentAlarmListBinding binding;

    private NavController navController;

    private Context context;

    private ListViewModel viewModel;

    private ListAdapter listAdapter;

    private ActionMode actionMode;

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

        requireActivity().addMenuProvider(this, getViewLifecycleOwner());

        listAdapter = new ListAdapter(context, this);
        binding.rvAlarmList.setAdapter(listAdapter);

        checkActivityExtras();

        viewModel = new ViewModelProvider(
                this,
                new AlarmListFragmentViewModelFactory(requireContext())
        ).get(ListViewModel.class);

        viewModel.liveFlagOfDeletionAlarmsFromActionMode.observe(getViewLifecycleOwner(), b -> {
            if (actionMode != null) {
                actionMode.finish();
                actionMode = null;
                setNextAlarmTimeInUI();
            }
        });

        viewModel.liveAlarms.observe(getViewLifecycleOwner(), alarms -> {
            listAdapter.setAlarmsToAdapter(alarms);
        });

        binding.rvAlarmList.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getAllAlarmsFromDatabase();

        setNextAlarmTimeInUI();
    }

    private void setNextAlarmTimeInUI() {
        try {
            long timeInMillis = new AlarmClockManager(context).getNextAlarmTimeInMillis();
            binding.nextAlarmInscription.setText(getString(R.string.next_alarm_inscription_for_list));
            binding.nextAlarmTime.setText(Tools.getFormattedTimeTittleFromMillis(timeInMillis));
            binding.nextAlarmDate.setText(Tools.getDateTittleFromMillis(timeInMillis));
        } catch (NullPointerException e) {
            binding.nextAlarmInscription.setText("");
            binding.nextAlarmTime.setText(getString(R.string.time_zero_zero));
            binding.nextAlarmDate.setText(getString(R.string.no_alarms));
        }
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.fragment_list_menu, menu);
    }

    @Override
    public void onListItemClick(Alarm alarm, ListAdapter.ViewHolder holder) {
        if (actionMode != null) {
            boolean contains = viewModel.liveAlarmsInActionMode.getValue().contains(alarm);
            if (contains) {
                viewModel.removeAlarmFromSelectedItemsInActionMode(alarm, holder);
                listAdapter.setDrawable(holder, R.drawable.alarm_list_item_bg);
            } else {
                viewModel.addItemToSelectedItemsInActionMode(alarm, holder);
                listAdapter.setDrawable(holder, R.drawable.alarm_list_selected_item_bg);
            }
        }
    }

    @Override
    public void onListItemLongClick(Alarm alarm, ListAdapter.ViewHolder holder) {
        if (actionMode == null) {
            setActionMode();
        }
    }

    @Override
    public void onEnableSwitchClick(Alarm alarm, boolean isChecked) {
        viewModel.updateAlarmState(alarm, isChecked);
        setNextAlarmTimeInUI();
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.miAdd) {
            if (!Settings.canDrawOverlays(requireActivity())) {
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(context)
                        .setTitle(getString(R.string.can_draw_overlays_alert_tittle))
                        .setMessage(getString(R.string.can_draw_overlays_alert_message))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), ((dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
                                    "package:" + context.getPackageName()
                            ));
                            startActivityForResult(intent, 9);
                        }));
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            } else {
                navigate(R.id.action_firstFragment_to_alarmEditFragment);
            }
        }
        return true;
    }

    private void checkActivityExtras() {
        Intent intent = requireActivity().getIntent();
        boolean extrasExist = Tools.checkActivityExtras(intent);
        if (extrasExist) {
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context)
                    .setTitle(context.getString(R.string.attention))
                    .setMessage(context.getString(R.string.time_zone_changed))
                    .setPositiveButton(context.getString(R.string.ok), (dialog, which) -> {
                        // nothing
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            intent.removeExtra("time_zone_changed");
        }
    }

    private void navigate(int destination) {
        navController.navigate(destination);
    }

    private void setActionMode() {

        ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.fragment_list_action_mode_menu, menu);

                actionMode = mode;

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.miDelete) {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(context)
                            .setTitle(getString(R.string.confirm))
                                    .setPositiveButton(getString(R.string.delete), ((dialog, which) -> {
                                        viewModel.deleteSelectedAlarmsInActionMode();
                                    }))
                            .setNegativeButton(getString(R.string.cansel), ((dialog, which) -> {
                                // nothing
                            }));
                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();
                }

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
                viewModel.clearListOfAlarmsInActionMode();
                listAdapter.setDrawableForAllHolders(viewModel.getHoldersOfSelectedItems(), R.drawable.alarm_list_item_bg);
            }
        };

        requireActivity().startActionMode(actionModeCallback);
    }
}