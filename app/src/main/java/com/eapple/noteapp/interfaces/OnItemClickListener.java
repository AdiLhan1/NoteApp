package com.eapple.noteapp.interfaces;

import com.eapple.noteapp.model.TaskModel;

public interface OnItemClickListener {
    void onItemClick(int position, TaskModel taskModel);

    void onLongClick(int position);
}
