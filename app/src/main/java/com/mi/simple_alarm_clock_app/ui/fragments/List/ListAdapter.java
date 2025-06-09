package com.mi.simple_alarm_clock_app.ui.fragments.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.ui.fragments.AlarmListFragment;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public interface ListAdapterListener {
        void onListItemClick(Alarm alarm, ViewHolder viewHolder);
        void onListItemLongClick(Alarm alarm, ViewHolder holder);
        void onEnableSwitchClick(Alarm alarm, boolean isChecked);
    }

    private final String TAG = "ListAdapter";

    private ListAdapterListener listener;

    public List<Alarm> alarms;

    private final Context context;

    private final LayoutInflater inflater;

    public ListAdapter(Context context, AlarmListFragment alarmListFragment) {
        inflater = LayoutInflater.from(context);
        this.context = context;

        listener = alarmListFragment;

        alarms = new ArrayList<>();
    }

    public void setAlarmsToAdapter(List<Alarm> alarms) {
        updateAlarmsList(alarms);
    }

    public void updateAlarmsList(List<Alarm> alarms) {
        this.alarms.clear();
        this.alarms.addAll(alarms);
        notifyDataSetChanged();
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

        if (alarm instanceof RepeatingAlarm) {
            holder.daysOfWeek.setText(getDaysOfWeekTittle((RepeatingAlarm) alarm));
        }

        holder.alarmName.setText(getAlarmNameTittle(alarm.getName()));

        String alarmTimeTittle = Tools.getFormattedTimeTittleFromMillis(alarm.getTimeInMillis());

        holder.alarmTime.setText(alarmTimeTittle);

        holder.enableSwitch.setChecked(alarm.isEnabled());

        holder.enableSwitch.setOnClickListener(v -> {
            boolean isChecked = holder.enableSwitch.isChecked();
            listener.onEnableSwitchClick(alarm, isChecked);
        });

        holder.itemContainer.setOnClickListener(v -> {
            listener.onListItemClick(alarm, holder);
        });

        holder.itemContainer.setOnLongClickListener(v -> {
            listener.onListItemLongClick(alarm, holder);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View itemContainer;
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
        if (a.isTuesday()) sb.append(context.getString(R.string.tuesday_short_tittle)).append(" ");
        if (a.isWednesday()) sb.append(context.getString(R.string.wednesday_short_tittle)).append(" ");
        if (a.isThursday()) sb.append(context.getString(R.string.thursday_short_tittle)).append(" ");
        if (a.isFriday()) sb.append(context.getString(R.string.friday_short_tittle)).append(" ");
        if (a.isSaturday()) sb.append(context.getString(R.string.saturday_short_tittle)).append(" ");
        if (a.isSunday()) sb.append(context.getString(R.string.sunday_short_tittle));

        return sb.toString();
    }

    public void setDrawable(ViewHolder holder, int drawableId) {
        holder.itemContainer.setBackground(
                AppCompatResources.getDrawable(
                        context,
                        drawableId
                )
        );
    }

    public void setDrawableForAllHolders(ArrayList<RecyclerView.ViewHolder> holders, int drawableId) {
        for (RecyclerView.ViewHolder holder : holders) {
            setDrawable((ViewHolder) holder, drawableId);
        };
    }
}
