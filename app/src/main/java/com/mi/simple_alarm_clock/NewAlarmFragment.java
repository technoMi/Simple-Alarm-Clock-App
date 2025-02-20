package com.mi.simple_alarm_clock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mi.simple_alarm_clock.databinding.FragmentNewAlarmBinding;


public class NewAlarmFragment extends Fragment {

    private FragmentNewAlarmBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewAlarmBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
