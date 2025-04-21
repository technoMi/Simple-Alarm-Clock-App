package com.mi.simple_alarm_clock_app.ui.fragments.List;

import android.content.Context;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.alarmclock.AlarmManager;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Alarm> alarms;

    private final Context context;

    private LayoutInflater inflater;

    private boolean actionMode;

    private ArrayList<Alarm> selectedItemsInActionMode;

    public ListAdapter(Context context, List<Alarm> alarms) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.alarms = alarms;

        actionMode = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_alarm_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Alarm alarm = alarms.get(position);

        if (alarm instanceof SingleAlarm) {
            // do nothing
        }
        if (alarm instanceof RepeatingAlarm) {
            holder.daysOfWeek.setText(getDaysOfWeekTittle((RepeatingAlarm) alarm));
        }

        holder.alarmName.setText(getAlarmNameTittle(alarm.getName()));

        String alarmTimeTittle = Tools.getFormattedTimeTittleFromMillis(alarm.getTimeInMillis());

        holder.alarmTime.setText(alarmTimeTittle);

        holder.enableSwitch.setChecked(alarm.isEnabled());

        holder.enableSwitch.setOnClickListener(v -> {
            alarm.setEnabled(!alarm.isEnabled());
            if (holder.enableSwitch.isChecked()) {
                new AlarmManager(context).setAlarmClockInSystemManager(alarm);
            } else {
                new AlarmManager(context).canselAlarmClockInSystemManager(alarm);
            }

            new DatabaseManager().updateAlarm(alarm);
        });

        holder.itemContainer.setOnClickListener(v -> {
            if (actionMode) {
                if (selectedItemsInActionMode.contains(alarm)) {
                    selectedItemsInActionMode.remove(alarm);
                    setDrawable(holder.itemContainer, R.drawable.alarm_list_item_bg);
                } else {
                    selectedItemsInActionMode.add(alarm);
                    setDrawable(holder.itemContainer, R.drawable.alarm_list_selected_item_bg);
                }
            }
        });

        holder.itemContainer.setOnLongClickListener(v -> {
            if (!actionMode) {
                actionMode = true;
                selectedItemsInActionMode = new ArrayList<>();

                setDrawable(holder.itemContainer, R.drawable.alarm_list_selected_item_bg);

                setActionMode();
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemContainer;
        TextView alarmName;
        TextView alarmTime;
        TextView daysOfWeek;
        MaterialSwitch enableSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemContainer = itemView.findViewById(R.id.itemContainer);
            alarmName = itemView.findViewById(R.id.tvAlarmName);
            alarmTime = itemView.findViewById(R.id.tvAlarmTime);
            daysOfWeek = itemView.findViewById(R.id.tvDaysOfWeek);
            enableSwitch = itemView.findViewById(R.id.enableSwitch);
        }
    }

    private String getAlarmNameTittle(String name) {
        return (name.isEmpty() ? context.getString(R.string.alarm_no_name) : name);
    }

    private String getDaysOfWeekTittle(RepeatingAlarm a) {

        StringBuilder sb = new StringBuilder();

        boolean allChecked = a.isMonday() && a.isTuesday() && a.isWednesday() && a.isThursday()
                && a.isFriday() && a.isSaturday() && a.isSunday();

        if (allChecked) {
            return context.getString(R.string.everyday_mode_tittle);
        }

        if (a.isMonday()) sb.append(context.getString(R.string.monday_short_tittle)).append(" ");
        if (a.isTuesday()) sb.append(context.getString(R.string.tuesday_short_tittle)).append(" ");;
        if (a.isWednesday()) sb.append(context.getString(R.string.wednesday_short_tittle)).append(" ");;
        if (a.isThursday()) sb.append(context.getString(R.string.thursday_short_tittle)).append(" ");;
        if (a.isFriday()) sb.append(context.getString(R.string.friday_short_tittle)).append(" ");;
        if (a.isSaturday()) sb.append(context.getString(R.string.saturday_short_tittle)).append(" ");;
        if (a.isSunday()) sb.append(context.getString(R.string.sunday_short_tittle));

        return sb.toString();
    }

    private void setDrawable(View view, int drawableId) {
        view.setBackground(
                AppCompatResources.getDrawable(
                        context,
                        drawableId
                )
        );
    }

    private void setActionMode() {
        ActionMode.Callback callback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.fragment_list_action_mode_menu,menu);

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
                    for (Alarm alarm : selectedItemsInActionMode) {
                        new AlarmManager(context).canselAlarmClockInSystemManager(alarm);
                        new DatabaseManager().deleteAlarm(alarm);
                        selectedItemsInActionMode.remove(alarm);
                    }
                    notifyDataSetChanged();
                    actionMode.finish();
                }
                if (itemId == R.id.miSelectAll) {

                }

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = false;
                selectedItemsInActionMode.clear();
            }
        };

        ((AppCompatActivity) context).startActionMode(callback);
    }
}
