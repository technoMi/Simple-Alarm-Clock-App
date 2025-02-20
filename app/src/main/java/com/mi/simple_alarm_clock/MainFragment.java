package com.mi.simple_alarm_clock;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mi.simple_alarm_clock.databinding.FragmentMainBinding;


public class MainFragment extends Fragment {

    private Context context;

    private FragmentMainBinding binding;

    private OnFragmentNavigation onFragmentNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireContext();

        if (context instanceof OnFragmentNavigation) {
            onFragmentNavigation = (OnFragmentNavigation) context;
        } else {
            throw new RuntimeException(context + "must implement OnFragmentNavigation");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View mView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(mView, savedInstanceState);
//
//        binding.btnAddAlarm.setOnClickListener(view -> {
//            onFragmentNavigation.onNavigationForward(R.id.newAlarmFragment);
//        });

//        binding.btnSettings.setOnClickListener(view -> {
//            onFragmentNavigation.onNavigationForward(new SettingsFragment());
//        });
    }
}