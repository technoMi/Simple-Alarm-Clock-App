package com.mi.simple_alarm_clock_app.ui.fragments.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.mi.simple_alarm_clock_app.R;
import com.mi.simple_alarm_clock_app.Tools;
import com.mi.simple_alarm_clock_app.alarmclock.AlarmClockManager;
import com.mi.simple_alarm_clock_app.alarmclock.TimeUtils;
import com.mi.simple_alarm_clock_app.database.DatabaseManager;
import com.mi.simple_alarm_clock_app.model.Alarm;
import com.mi.simple_alarm_clock_app.model.RepeatingAlarm;
import com.mi.simple_alarm_clock_app.model.SingleAlarm;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Alarm> alarms;
;

    private final Context context;

    private LayoutInflater inflater;

    public ListAdapter(Context context, List<Alarm> alarms) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.alarms = alarms;
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
                new AlarmClockManager(context).setAlarmClockInSystemManager(alarm);
            } else {
                new AlarmClockManager(context).canselAlarmClockInSystemManager(alarm);
            }

            new DatabaseManager().updateAlarm(alarm);
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView alarmName;
        TextView alarmTime;
        TextView daysOfWeek;
        MaterialSwitch enableSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmName = itemView.findViewById(R.id.tvAlarmName);
            alarmTime = itemView.findViewById(R.id.tvAlarmTime);
            daysOfWeek = itemView.findViewById(R.id.tvDaysOfWeek);
            enableSwitch = itemView.findViewById(R.id.enableSwitch);
        }
    }

    private String getAlarmNameTittle(String name) {
        return (name.equals("") ? context.getString(R.string.alarm_no_name) : name);
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
}
