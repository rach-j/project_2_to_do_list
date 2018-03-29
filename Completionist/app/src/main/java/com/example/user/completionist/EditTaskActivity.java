package com.example.user.completionist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    DatabaseHelper db;
    String simpleDateFromCalendar;
    EditText editTextTaskTitle, editTextTaskDescription;
    Spinner priorityStatus;
    TextView textViewTaskStatus, textViewSelectedDate;
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
        textViewSelectedDate = findViewById(R.id.editActivityTextViewSelectedDeadline);

        editTextTaskTitle.setText(task.getTaskTitle());
        editTextTaskDescription.setText(task.getTaskDescription());
        priorityStatus.setSelection(task.getPriorityStatus());
//      Is this okay? In my string file I've got an array of priority levels where the position in
// the array is the same as the rating in the table (so e.g. high is level 0 in the table and also
// position 0 in the array), but that's just because I've set it up that way. Is there a better way?
        textViewSelectedDate.setText(getDeadlineForDisplay(task));
        textViewTaskStatus.setText(getCompletionStatusForDisplay(task));
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
        TextView date = findViewById(R.id.editActivityTextViewSelectedDeadline);

        date.setText(df.format(calendar.getTime()));
    }

    public void onCalendarButtonClick(View view) {
        android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }

    public void onSaveButtonClicked(View view) {
        String title = editTextTaskTitle.getText().toString().trim();
        String description = editTextTaskDescription.getText().toString().trim();
        Integer priority = priorityStatus.getSelectedItemPosition();
        String deadline = getDeadlineForDatabase(simpleDateFromCalendar, task);

        if(db.editEntry(task.getId(), title, description, priority, deadline)) {
            Toast.makeText(this, "Task Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error: Task Not Updated", Toast.LENGTH_LONG).show();
        }
    }

    public String getCompletionStatusForDisplay(Task task) {
        if (task.getCompletionStatus() == 1) {
            return getResources().getString(R.string.complete_status);
        } else {
            return getResources().getString(R.string.not_complete_status);
        }
    }

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

    public String getDeadlineForDatabase(String simpleDateFromCalendar, Task task) {
        if (simpleDateFromCalendar == null) {
            return task.getDeadline();
        } else {
            return simpleDateFromCalendar;
        }
    }
}
