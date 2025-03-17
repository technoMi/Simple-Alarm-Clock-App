package com.mi.simple_alarm_clock_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

public class DatabaseManager {
    public static AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database.db").build()
;    }
}
