package com.example.user.completionist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditTaskActivity extends AppCompatActivity {

    DatabaseHelper db;
    String completionStatus;
    EditText editTextTaskTitle, editTextTaskDescription;
    Spinner priorityStatus;
    TextView textViewTaskStatus;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        this.task = (Task) intent.getSerializableExtra("task");
        db = new DatabaseHelper(this);
        editTextTaskTitle = findViewById(R.id.editActivityEditTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editActivityEditTextTaskDescription);
        priorityStatus = findViewById(R.id.editActivitySpinnerPriorityStatus);
        textViewTaskStatus = findViewById(R.id.editActivityTextViewCompletionStatus);

        if (task.getCompletionStatus() == 1) {
            completionStatus = getResources().getString(R.string.complete_status);
        } else {
            completionStatus = getResources().getString(R.string.not_complete_status);
        }

        editTextTaskTitle.setText(task.getTaskTitle());
        editTextTaskDescription.setText(task.getTaskDescription());
        priorityStatus.setSelection(task.getPriorityStatus());
//      Is this okay? In my string file I've got an array of priority levels where the position in
// the array is the same as the rating in the table (so e.g. high is level 0 in the table and also
// position 0 in the array), but that's just because I've set it up that way. Is there a better way?
        textViewTaskStatus.setText(completionStatus);
    }

    public void onSaveButtonClicked(View view) {
        String title = editTextTaskTitle.getText().toString().trim();
        String description = editTextTaskDescription.getText().toString().trim();
        Integer priority = priorityStatus.getSelectedItemPosition();
        String deadline = "YY-MM-DD";

        if(db.editEntry(task.getId(), title, description, priority, deadline)) {
            Toast.makeText(this, "Task Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error: Task Not Updated", Toast.LENGTH_LONG).show();
        }
    }
}
