package com.example.user.completionist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditTaskActivity extends AppCompatActivity {

    private DatabaseHelper db;
    String completionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        db = new DatabaseHelper(this);
        EditText editTextTaskTitle, editTextTaskDescription;
        TextView textViewTaskStatus;

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        Log.d("task", task.getTaskTitle());

        editTextTaskTitle = findViewById(R.id.editTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTaskDescription);
        textViewTaskStatus = findViewById(R.id.taskStatusDetails);

        if (task.getCompletionStatus() == 1) {
            completionStatus = getResources().getString(R.string.complete_status);
        } else {
            completionStatus = getResources().getString(R.string.not_complete_status);
        }

        editTextTaskTitle.setText(task.getTaskTitle());
        editTextTaskDescription.setText(task.getTaskDescription());
        textViewTaskStatus.setText(completionStatus);
    }

}
