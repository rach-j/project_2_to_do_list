package com.example.user.completionist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewTaskActivity extends AppCompatActivity {

    private DatabaseHelper db;
    TextView textViewTaskTitle, textViewTaskDescription, textViewCompletionStatus, textViewPriorityStatus, textViewDeadline;
    String completionStatus, priorityStatus;
    Button editButton;
    Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        this.selectedTask = (Task) intent.getSerializableExtra("task");
        Log.d("TaskActivity", selectedTask.getTaskTitle());

        textViewTaskTitle = findViewById(R.id.viewActivityTextViewTaskTitle);
        textViewTaskDescription = findViewById(R.id.viewActivityTextViewTaskDescription);
        textViewPriorityStatus = findViewById(R.id.viewActivityTextViewPriorityStatus);
        textViewCompletionStatus = findViewById(R.id.viewActivityTextViewCompletionStatus);
        textViewDeadline = findViewById(R.id.viewActivityTextViewDeadlineLabel);
        editButton = findViewById(R.id.viewActivityButtonEdit);

        if (selectedTask.getCompletionStatus() == 1) {
            completionStatus = getResources().getString(R.string.complete_status);
        } else {
            completionStatus = getResources().getString(R.string.not_complete_status);
        }

        if(selectedTask.getPriorityStatus().equals(0)) {
            priorityStatus = getResources().getString(R.string.high_priority);
        } else if(selectedTask.getPriorityStatus().equals(1)) {
            priorityStatus = getResources().getString(R.string.medium_priority);
        } else if (selectedTask.getPriorityStatus().equals(2)) {
            priorityStatus = getResources().getString(R.string.low_priority);
        } else {
            priorityStatus = getResources().getString(R.string.no_priority);
        }

        String deadline = null;
        if (selectedTask.getDeadline().equals("")) {
            deadline = "";
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date date = null;
            try {
                date = sdf.parse(selectedTask.getDeadline());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

            deadline = dateFormat.format(date);
        }

        textViewTaskTitle.setText(selectedTask.getTaskTitle());
        textViewTaskDescription.setText(selectedTask.getTaskDescription());
        textViewPriorityStatus.setText(priorityStatus);
        textViewDeadline.setText(getResources().getString(R.string.deadline) + " " + deadline);
        textViewCompletionStatus.setText(completionStatus);

        if(selectedTask.getCompletionStatusForCheckBox()) {
            editButton.setEnabled(false);
        } else {
            editButton.setEnabled(true);
        }
//        Can't edit completed tasks
    }

    public void onDeleteItemClicked(View view) {
        final Task selectedTask = this.selectedTask;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Context context = this;

        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(db.deleteEntry(selectedTask.getId())) {
                    Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onEditItemClicked(View view) {
        Intent intentForEditActivity = new Intent(this, EditTaskActivity.class);
        intentForEditActivity.putExtra("task", selectedTask);
        startActivity(intentForEditActivity);
    }
}
