package com.example.user.completionist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private List<Task> taskList;
    private ListView listView;
    private Button addNewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        taskList = new ArrayList<>();
        listView = findViewById(R.id.listViewTasks);
        addNewButton = findViewById(R.id.buttonAddNewTask);

        loadEntriesFromDatabase();
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
                        cursor.getInt(3)
                ));
            } while (cursor.moveToNext());

            TaskAdapter adapter = new TaskAdapter(this, R.layout.list_layout_tasks, taskList, db);
            listView.setAdapter(adapter);
        }
    }

    public void onCheckBoxClicked(View view) {
        Task task = (Task) view.getTag();
                if (db.markAsComplete(task.getId())) {
                    loadEntriesFromDatabase();
//                    Toast.makeText(this, "Task marked as complete", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(this, MainActivity.class);
//                    intent.putExtra("task", task);
//                    startActivity(intent);
//                    Is there a better way to prevent reticking other than refreshing page?
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
}
