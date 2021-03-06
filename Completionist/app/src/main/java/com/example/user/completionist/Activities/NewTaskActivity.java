package com.example.user.completionist.Activities;

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
import com.example.user.completionist.DatabaseTools.DatabaseHelper;
import com.example.user.completionist.AndroidDisplayHelpers.DatePickerFragment;
import com.example.user.completionist.R;
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
    String simpleDateFromCalendar;

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
        simpleDateFromCalendar = null;

        editPriorityStatus.setSelection(3);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFromCalendar = sdf.format(calendar.getTime());

        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
        TextView date = findViewById(R.id.newActivityTextViewSelectedDeadline);

        date.setText(df.format(calendar.getTime()));
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

        String deadline = simpleDateFromCalendar;

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
}
