package com.eapple.noteapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.eapple.noteapp.model.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM taskmodel")
    LiveData<List<TaskModel>> getAll();

    @Insert
    void insert(TaskModel taskModel);

    @Delete
    void delete(TaskModel model);
}
