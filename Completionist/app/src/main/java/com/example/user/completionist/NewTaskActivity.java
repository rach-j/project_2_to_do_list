package com.example.user.completionist;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by user on 24/03/2018.
 */

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DatabaseHelper db;
    EditText editTextTaskTitle, editTextTaskDescription;
    Spinner editPriorityStatus;
    Button buttonConfirmAdd;
    ImageButton buttonCalendar;
    TextView selectedDate;
    String dateForDb;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_new_task);

        db = new DatabaseHelper(this);
        editTextTaskTitle = findViewById(R.id.newActivityEditTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.newActivityEditTextTaskDescription);
        editPriorityStatus = findViewById(R.id.newActivitySpinnerPriorityStatus);
        buttonConfirmAdd = findViewById(R.id.newActivitySaveButton);
        buttonCalendar = findViewById(R.id.newActivityButtonCalendar);
        selectedDate = findViewById(R.id.newActivityTextViewSelectedDeadline);
        dateForDb = null;

        editPriorityStatus.setSelection(3);
    }

    public void onAddClick(View view) {
        addTask();
    }

    public void onCalendarButtonClick(View view) {
        android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }

    private void addTask() {
        String taskTitle = editTextTaskTitle.getText().toString().trim();
        String taskDescription = editTextTaskDescription.getText().toString().trim();
        Integer priority = editPriorityStatus.getSelectedItemPosition();
        //      Is this okay? In my string file I've got an array of priority levels where the position in
// the array is the same as the rating in the table (so e.g. high is level 0 in the table and also
// position 0 in the array), but that's just because I've set it up that way. Is there a better way?

        String deadline = dateForDb;

        if (taskTitle.isEmpty()) {
            editTextTaskTitle.setError("Task cannot be empty");
            editTextTaskTitle.requestFocus();
            return;
        }

        if (db.addTask(taskTitle, taskDescription, priority, deadline)) {
            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error: Task Not Added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateForDb = sdf.format(calendar.getTime());

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        String selectedDate = dateFormat.format(calendar.getTime());
        TextView date = findViewById(R.id.newActivityTextViewSelectedDeadline);
        date.setText(selectedDate);
    }
}
