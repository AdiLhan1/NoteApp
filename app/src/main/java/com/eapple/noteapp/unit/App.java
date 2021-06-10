package com.eapple.noteapp.unit;

import android.app.Application;

import androidx.room.Room;

import com.eapple.noteapp.db.AppDatabase;

public class App extends Application {
    public static AppDatabase instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = Room.
                databaseBuilder(this, AppDatabase.class, "task-database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        PreferencesHelper.init(this);
    }

    public static AppDatabase getInstance() {
        return instance;
    }
}
