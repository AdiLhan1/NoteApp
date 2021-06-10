package com.eapple.noteapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TaskModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String date;
    private String color;

    public TaskModel(String title, String date, String color) {
        this.title = title;
        this.date = date;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
