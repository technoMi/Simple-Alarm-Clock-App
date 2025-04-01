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
import com.mi.simple_alarm_clock_app.model.Alarm;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Alarm> alarms;

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
        holder.alarmName.setText(alarm.getName());
        holder.enableSwitch.setChecked(alarm.isEnabled());
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
}
