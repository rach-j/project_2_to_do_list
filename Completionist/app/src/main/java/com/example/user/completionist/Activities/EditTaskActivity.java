package com.example.user.completionist.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.user.completionist.AndroidDisplayHelpers.DisplayHelper;
import com.example.user.completionist.DatabaseTools.DatabaseHelper;
import com.example.user.completionist.AndroidDisplayHelpers.DatePickerFragment;
import com.example.user.completionist.R;
import com.example.user.completionist.Task;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.priorities, R.layout.spinner_style);
//        priorityStatus.setAdapter(adapter);

//        To change colour of spinner need to do the above but then default selection doesn't get set.

        priorityStatus.setSelection(task.getPriorityStatus());
        textViewSelectedDate.setText(DisplayHelper.getDeadlineForDisplay(task, this));
        textViewTaskStatus.setText(DisplayHelper.getCompletionStatusForDisplay(task, this));
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

    public String getDeadlineForDatabase(String simpleDateFromCalendar, Task task) {
        if (simpleDateFromCalendar == null) {
            return task.getDeadline();
        } else {
            return simpleDateFromCalendar;
        }
    }
}
