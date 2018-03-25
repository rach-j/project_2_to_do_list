package com.example.user.completionist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

    public void onAddTaskClicked(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }
}
