package com.example.user.completionist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private List<Task> taskList;
    private ListView listView;
    private Button addNewButton;
    private ToggleButton priorityToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        taskList = new ArrayList<>();
        listView = findViewById(R.id.listViewTasks);
        addNewButton = findViewById(R.id.buttonAddNewTask);

//        loadEntriesFromDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        priorityToggleButton = findViewById(R.id.togglePriorityOrder);
        if(priorityToggleButton.isChecked()) {
            loadEntriesFromDatabaseInPriorityOrder();
        } else {
            loadEntriesFromDatabase();
        }
    }

    private void loadEntriesFromDatabase() {
        Cursor cursor = db.getAllTasks();

        taskList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                taskList.add(new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                ));
            } while (cursor.moveToNext());

            TaskAdapter adapter = new TaskAdapter(this, R.layout.list_layout_tasks, taskList, db);
            listView.setAdapter(adapter);
        }
    }

    public void onCheckBoxClicked(View view) {
        Task task = (Task) view.getTag();
                if (db.markAsComplete(task.getId())) {
                    priorityToggleButton = findViewById(R.id.togglePriorityOrder);
                    if(priorityToggleButton.isChecked()) {
                        loadEntriesFromDatabaseInPriorityOrder();
                    } else {
                        loadEntriesFromDatabase();
                    }
                    Toast.makeText(this, "Task marked as complete", Toast.LENGTH_SHORT).show();
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
        if(selectedTask == null) {
            Log.d("tagError", "getTag or setTag not working");
        }
    }

    private void loadEntriesFromDatabaseInPriorityOrder() {
        Cursor cursor = db.getAllTasksOrderedByPriority();

        taskList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                taskList.add(new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                ));
            } while (cursor.moveToNext());

            TaskAdapter adapter = new TaskAdapter(this, R.layout.list_layout_tasks, taskList, db);
            listView.setAdapter(adapter);
        }
    }

    public void onPriorityToggleButtonClicked(View view) {

        priorityToggleButton = findViewById(R.id.togglePriorityOrder);
        if(priorityToggleButton.isChecked()) {
            loadEntriesFromDatabaseInPriorityOrder();
        } else {
            loadEntriesFromDatabase();
        }
    }
}
