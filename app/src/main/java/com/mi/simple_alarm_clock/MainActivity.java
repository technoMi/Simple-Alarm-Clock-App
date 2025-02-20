package com.mi.simple_alarm_clock;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.mi.simple_alarm_clock.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements OnFragmentNavigation {

    private ActivityMainBinding binding;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
    }

    @Override
    public void onNavigationForward(int fragment) {
       // navController.navigate(fragment);
    }

    @Override
    public void onNavigationBack() {
       // navController.popBackStack();
    }
}