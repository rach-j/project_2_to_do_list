package com.example.user.completionist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    List<Task> taskList;
    ListView listView;
    ToggleButton priorityToggleButton, viewAllToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        taskList = new ArrayList<>();
        listView = findViewById(R.id.listViewTasks);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkToggleStatusAndLoadEntries();
    }

    private void loadEntriesFromDatabase() {
        Cursor cursor = db.getAllTasks();
        setUpAdapterAndRun(cursor);
   }

    private void loadEntriesFromDatabaseInPriorityOrder() {
        Cursor cursor = db.getAllTasksOrderedByPriority();
        setUpAdapterAndRun(cursor);
    }

    private void loadOnlyIncompleteEntries() {
        Cursor cursor = db.getOnlyIncompleteTasks();
        setUpAdapterAndRun(cursor);
    }

    private void loadOnlyIncompleteEntriesInPriorityOrder() {
        Cursor cursor = db.getOnlyIncompleteTasksOrderedByPriority();
        setUpAdapterAndRun(cursor);
    }

    public void onCheckBoxClicked(View view) {
        Task task = (Task) view.getTag();
                if (db.markAsComplete(task.getId())) {
                    Toast.makeText(this, "Task marked as complete", Toast.LENGTH_SHORT).show();
                    checkToggleStatusAndLoadEntries();
                } else {
                    Toast.makeText(this, "Error: task not marked as complete", Toast.LENGTH_SHORT).show();
                }
            }

    public void onAddTaskClicked(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    public void onListItemClicked(View listItem) {
        Task selectedTask = (Task) listItem.getTag();
        Intent intent = new Intent(this, ViewTaskActivity.class);

        intent.putExtra("task", selectedTask);
        startActivity(intent);
    }


    public void onPriorityToggleButtonClicked(View view) {
        checkToggleStatusAndLoadEntries();
    }

    public void onViewAllToggleButtonClicked(View view) {
        checkToggleStatusAndLoadEntries();
    }

    public void checkToggleStatusAndLoadEntries() {
        priorityToggleButton = findViewById(R.id.mainActivityToggleButtonPriorityOrder);
        viewAllToggleButton = findViewById(R.id.mainActivityToggleButtonViewAll);
        if (priorityToggleButton.isChecked()) {
            if (viewAllToggleButton.isChecked()) {
                loadEntriesFromDatabaseInPriorityOrder();
            } else {
                loadOnlyIncompleteEntriesInPriorityOrder();
            }
        } else {
            if (viewAllToggleButton.isChecked()) {
                loadEntriesFromDatabase();
            } else {
                loadOnlyIncompleteEntries();
            }
        }
    }

    public void setUpAdapterAndRun(Cursor cursor) {
        taskList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                taskList.add(new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5)
                ));
            } while (cursor.moveToNext());

            TaskAdapter adapter = new TaskAdapter(this, R.layout.list_layout_tasks, taskList, db);
            listView.setAdapter(adapter);
        }
    }
}
