package com.example.user.completionist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;

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

        TextView textViewTaskTitle = listOfTasksView.findViewById(R.id.listLayoutTextViewTaskTitle);
        ImageView priorityImage = listOfTasksView.findViewById(R.id.listLayoutPriorityIcon);
        ImageView overdueImage = listOfTasksView.findViewById(R.id.listLayoutImageViewOverdue);
        final CheckBox checkBoxForCompletion = listOfTasksView.findViewById(R.id.listLayoutCheckBoxCompletionStatus);

        textViewTaskTitle.setText(currentTask.getTaskTitle());
        checkBoxForCompletion.setChecked(currentTask.getCompletionStatusForCheckBox());

        lockCheckBoxUponCompletion(checkBoxForCompletion);
        
        setPriorityIcon(currentTask, priorityImage);
        setOverdueIcon(currentTask, checkBoxForCompletion, overdueImage);
//              Overdue icon disappears on marking as complete as otherwise all items marked as
// complete will eventually look overdue.

        checkBoxForCompletion.setTag(currentTask);
        listOfTasksView.setTag(currentTask);

        return listOfTasksView;
    }

    public void setPriorityIcon(Task task, ImageView priorityImage) {
        if (task.getPriorityStatus().equals(0)) {
            priorityImage.setImageResource(R.drawable.custom_circle_red);
        } else if (task.getPriorityStatus().equals(1)) {
            priorityImage.setImageResource(R.drawable.custom_circle_amber);
        } else if (task.getPriorityStatus().equals(2)) {
            priorityImage.setImageResource(R.drawable.custom_circle_green);
        }
    }

    public void lockCheckBoxUponCompletion(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            checkBox.setEnabled(false);
        } else {
            checkBox.setEnabled(true);
        }
    }

    public void setOverdueIcon(Task task, CheckBox checkBox, ImageView overdueImage) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = sdf.format(calendar.getTime());

        if (db.isTableEmpty()) {
        } else {
            if (task.getDeadline() != null
                    && currentTime.compareTo(task.getDeadline()) > 0
                    && !checkBox.isChecked() ) {
                overdueImage.setImageResource(R.drawable.overdue_icon);
            }
        }
    }
}