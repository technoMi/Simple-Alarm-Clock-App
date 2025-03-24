package com.mi.simple_alarm_clock_app.database;

import android.content.Intent;

import java.util.Random;

public class DatabaseManager {
    public static void getNewItemIDForDatabase() {
        int id = new Random().nextInt();


    }
}
