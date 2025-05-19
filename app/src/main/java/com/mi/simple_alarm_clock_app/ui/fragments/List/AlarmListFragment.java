package com.mi.simple_alarm_clock_app.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.Settings;
import android.util.Log;
import android.view.ActionMode;
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
import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.databinding.FragmentAlarmListBinding;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.ui.fragments.List.AlarmListFragmentViewModelFactory;
import com.mi.simple_alarm_clock_app.ui.fragments.List.ListAdapter;
import com.mi.simple_alarm_clock_app.ui.fragments.List.ListViewModel;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlarmListFragment extends Fragment implements MenuProvider, ListAdapter.ListAdapterListener {

    private final String TAG = "AlarmListFragment";

    private FragmentAlarmListBinding binding;

    private NavController navController;

    private Context context;

    private ListViewModel viewModel;

    private ListAdapter listAdapter;

    private ActionMode.Callback actionModeCallback;

    private boolean actionMode;

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

        viewModel.liveAlarms.observe(getViewLifecycleOwner(), alarms -> {
            listAdapter.setAlarmsToAdapter(alarms);
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
    public void onListItemClick(Alarm alarm, ListAdapter.ViewHolder holder) {
        if (actionMode) {
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
        if (!actionMode) {
            setActionMode();
        }
    }

    @Override
    public void onEnableSwitchClick(Alarm alarm, boolean isChecked) {
        viewModel.updateAlarmState(alarm, isChecked);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.miSettings) {
            navigate(R.id.action_firstFragment_to_alarmEditFragment);
        }
        return true;
    }

    private void checkActivityExtras() {
        Tools.checkActivityExtras(context, requireActivity().getIntent());
    }

    private void navigate(int destination) {
        navController.navigate(destination);
    }

    private void setActionMode() {

        actionMode = true;

        binding.btnAdd.setVisibility(View.INVISIBLE);

        actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.fragment_list_action_mode_menu, menu);

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
                    viewModel.deleteSelectedAlarmsInActionMode();
                }

                actionMode.finish();

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = false;
                binding.btnAdd.setVisibility(View.VISIBLE);
                viewModel.clearListOfAlarmsInActionMode();
                listAdapter.setDrawableForAllHolders(viewModel.getHoldersOfSelectedItems(), R.drawable.alarm_list_item_bg);
            }
        };

        requireActivity().startActionMode(actionModeCallback);
    }
}