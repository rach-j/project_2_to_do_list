package com.example.user.completionist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

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
    }

    @Override
    public View getView(int position, @Nullable View listOfTasksView, @NonNull ViewGroup parent) {

        Task currentTask = taskList.get(position);

        if(listOfTasksView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            listOfTasksView = inflater.inflate(R.layout.list_layout_tasks, parent, false);
        }

        TextView textViewTaskTitle = listOfTasksView.findViewById(R.id.taskTitle);
        CheckBox checkBoxForCompletion = listOfTasksView.findViewById(R.id.completedCheckBox);

        textViewTaskTitle.setText(currentTask.getTaskTitle());
        checkBoxForCompletion.setChecked(currentTask.getCompletionStatusForCheckBox());

        listOfTasksView.setTag(currentTask);

        return listOfTasksView;
    }
}