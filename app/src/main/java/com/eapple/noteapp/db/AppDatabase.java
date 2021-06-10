package com.eapple.noteapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.eapple.noteapp.model.TaskModel;

@Database(entities = {TaskModel.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao getTaskDao();
}
