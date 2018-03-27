package com.example.user.completionist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditTaskActivity extends AppCompatActivity {

    DatabaseHelper db;
    String completionStatus;
    EditText editTextTaskTitle, editTextTaskDescription;
    TextView textViewTaskStatus;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        this.task = (Task) intent.getSerializableExtra("task");

        db = new DatabaseHelper(this);

        editTextTaskTitle = findViewById(R.id.editTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTaskDescription);
        textViewTaskStatus = findViewById(R.id.taskStatusDetails);

        if (task.getCompletionStatus() == 1) {
            completionStatus = getResources().getString(R.string.complete_status);
        } else {
            completionStatus = getResources().getString(R.string.not_complete_status);
        }

        editTextTaskTitle.setText(this.task.getTaskTitle());
        editTextTaskDescription.setText(task.getTaskDescription());
        textViewTaskStatus.setText(completionStatus);
    }

    public void onSaveButtonClicked(View view) {
        String title = editTextTaskTitle.getText().toString().trim();
        String description = editTextTaskDescription.getText().toString().trim();

        if(db.editEntry(this.task.getId(), title, description)) {
            Toast.makeText(this, "Task Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error: Task Not Updated", Toast.LENGTH_LONG).show();

        }
    }
}
