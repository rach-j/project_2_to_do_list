package com.example.user.completionist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by user on 24/03/2018.
 */

public class TaskAdapter extends ArrayAdapter<Task> {

    Context context;
    int resource;
    List<Task> taskList;
    DatabaseHelper db;

    public TaskAdapter(@NonNull Context context, int resource, List<Task> taskList, DatabaseHelper db) {
        super(context, resource, taskList);

        this.context = context;
        this.resource = resource;
        this.taskList = taskList;
        this.db = db;
    }

    @Override
    public View getView(int position, View listOfTasksView, ViewGroup parent) {

        Task currentTask = getItem(position);

//        if(listOfTasksView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            listOfTasksView = inflater.inflate(R.layout.list_layout_tasks, parent, false);
//        }
//        Need to do this so colours for priority are correct - way around to retain efficiency?

        TextView textViewTaskTitle = listOfTasksView.findViewById(R.id.taskTitle);
        ImageView image = listOfTasksView.findViewById(R.id.priorityImage);
        final CheckBox checkBoxForCompletion = listOfTasksView.findViewById(R.id.completedCheckBox);

        textViewTaskTitle.setText(currentTask.getTaskTitle());

        if(currentTask.getPriorityStatus().equals(0)) {
            image.setImageResource(R.drawable.custom_circle_red);
        } else if (currentTask.getPriorityStatus().equals(1)) {
            image.setImageResource(R.drawable.custom_circle_amber);
        } else if (currentTask.getPriorityStatus().equals(2)) {
            image.setImageResource(R.drawable.custom_circle_green);
        }

        checkBoxForCompletion.setChecked(currentTask.getCompletionStatusForCheckBox());

        if(checkBoxForCompletion.isChecked()) {
            checkBoxForCompletion.setEnabled(false);
        } else {
            checkBoxForCompletion.setEnabled(true);
        }

        listOfTasksView.setTag(currentTask);
        checkBoxForCompletion.setTag(currentTask);


        listOfTasksView.setTag(currentTask);

        return listOfTasksView;
    }
}