package com.example.user.completionist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewTaskActivity extends AppCompatActivity {

    private DatabaseHelper db;
    TextView textViewTaskTitle, textViewTaskDescription, textViewCompletionStatus;
    String completionStatus;
    Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        this.selectedTask = (Task) intent.getSerializableExtra("task");
        Log.d("TaskActivity", selectedTask.getTaskTitle());

        textViewTaskTitle = findViewById(R.id.taskTitleDetails);
        textViewTaskDescription = findViewById(R.id.taskDescriptionDetails);
        textViewCompletionStatus = findViewById(R.id.taskStatusDetails);

        if (selectedTask.getCompletionStatus() == 1) {
            completionStatus = getResources().getString(R.string.complete_status);
        } else {
            completionStatus = getResources().getString(R.string.not_complete_status);
        }

        textViewTaskTitle.setText(selectedTask.getTaskTitle());
        textViewTaskDescription.setText(selectedTask.getTaskDescription());
        textViewCompletionStatus.setText(completionStatus);

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
                    Intent intent2 = new Intent(context, MainActivity.class);
                    startActivity(intent2);
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
        Intent intent2 = new Intent(this, EditTaskActivity.class);
        intent2.putExtra("task", this.selectedTask);
        startActivity(intent2);
    }
}
