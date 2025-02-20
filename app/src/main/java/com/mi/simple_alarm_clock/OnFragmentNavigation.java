package com.mi.simple_alarm_clock;

import androidx.fragment.app.Fragment;

public interface OnFragmentNavigation {
    void onNavigationForward(int fragment);
    void onNavigationBack();
}
