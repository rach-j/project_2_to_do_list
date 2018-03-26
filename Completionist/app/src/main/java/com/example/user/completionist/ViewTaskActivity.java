package com.example.user.completionist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewTaskActivity extends AppCompatActivity {

    TextView textViewTaskTitle, textViewTaskDescription, textViewCompletionStatus;
    String completionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        Intent intent = getIntent();
        Task selectedTask = (Task) intent.getSerializableExtra("task");
        Log.d("TaskActivity", selectedTask.getTaskTitle());

        textViewTaskTitle = findViewById(R.id.taskTitleDetails);
        textViewTaskDescription = findViewById(R.id.taskDescriptionDetails);
        textViewCompletionStatus = findViewById(R.id.taskStatusDetails);

        if (selectedTask.getCompletionStatus() == 0) {
            completionStatus = getResources().getString(R.string.complete_status);
        } else {
            completionStatus = getResources().getString(R.string.not_complete_status);
        }

        textViewTaskTitle.setText(selectedTask.getTaskTitle());
        textViewTaskDescription.setText(selectedTask.getTaskDescription());
        textViewCompletionStatus.setText(completionStatus);
    }


}
