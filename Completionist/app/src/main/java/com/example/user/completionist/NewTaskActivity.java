package com.example.user.completionist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by user on 24/03/2018.
 */

public class NewTaskActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper db;
    EditText editTextTaskTitle, editTextTaskDescription;
    Spinner editPriorityStatus;
    Button buttonConfirmAdd;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_new_task);

        db = new DatabaseHelper(this);
        editTextTaskTitle = findViewById(R.id.titleInputNew);
        editTextTaskDescription = findViewById(R.id.descriptionInputNew);
        editPriorityStatus = findViewById(R.id.prioritySpinner);
        buttonConfirmAdd = findViewById(R.id.buttonConfirmAdd);

        editPriorityStatus.setSelection(3);
        buttonConfirmAdd.setOnClickListener(this);
    }

    public void onClick(View view) {
        addTask();
    }

    private void addTask() {
        String taskTitle = editTextTaskTitle.getText().toString().trim();
        String taskDescription = editTextTaskDescription.getText().toString().trim();
        Integer priority = editPriorityStatus.getSelectedItemPosition();

        if (taskTitle.isEmpty()) {
            editTextTaskTitle.setError("Task cannot be empty");
            editTextTaskTitle.requestFocus();
            return;
        }

        if (db.addTask(taskTitle, taskDescription, priority)) {
            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error: Task Not Added", Toast.LENGTH_SHORT).show();
        }
    }
}
