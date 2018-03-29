package com.example.user.completionist.Activities;

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
import com.example.user.completionist.DatabaseTools.DatabaseHelper;
import com.example.user.completionist.R;
import com.example.user.completionist.Task;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewTaskActivity extends AppCompatActivity {

    private DatabaseHelper db;
    TextView textViewTaskTitle, textViewTaskDescription, textViewCompletionStatus, textViewPriorityStatus, textViewDeadline;
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

        textViewTaskTitle.setText(selectedTask.getTaskTitle());
        textViewTaskDescription.setText(selectedTask.getTaskDescription());
        textViewPriorityStatus.setText(getPriorityStatusForDisplay(selectedTask));
        textViewDeadline.setText(getResources().getString(R.string.deadline) + " " + getDeadlineForDisplay(selectedTask));
        textViewCompletionStatus.setText(getCompletionStatusForDisplay(selectedTask));

        disableEditIfCompleted(selectedTask, editButton);
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

    public String getCompletionStatusForDisplay(Task task) {
        if (task.getCompletionStatus() == 1) {
            return getResources().getString(R.string.complete_status);
        } else {
            return getResources().getString(R.string.not_complete_status);
        }
    }

    public String getPriorityStatusForDisplay(Task task) {
        if(task.getPriorityStatus() == 0) {
            return getResources().getString(R.string.high_priority);
        } else if(task.getPriorityStatus() == 1) {
            return getResources().getString(R.string.medium_priority);
        } else if (task.getPriorityStatus() == 2) {
            return getResources().getString(R.string.low_priority);
        } else {
            return getResources().getString(R.string.no_priority);
        }
    }
//    Better way to do this?

    public String getDeadlineForDisplay(Task task) {
        if (task.getDeadline() == null) {
            return getResources().getString(R.string.no_deadline_set);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(task.getDeadline());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);

            return df.format(date);
        }
    }

    public void disableEditIfCompleted(Task task, Button editButton) {
        if(task.getCompletionStatusForCheckBox()) {
            editButton.setEnabled(false);
        } else {
            editButton.setEnabled(true);
        }
    }
}
