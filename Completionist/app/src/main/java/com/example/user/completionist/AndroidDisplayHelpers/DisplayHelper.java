package com.example.user.completionist.AndroidDisplayHelpers;

import android.content.Context;
import com.example.user.completionist.R;
import com.example.user.completionist.Task;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 29/03/2018.
 */

public class DisplayHelper {

        public static String getCompletionStatusForDisplay(Task task, Context context) {
            if (task.getCompletionStatus() == 1) {
                return context.getResources().getString(R.string.complete_status);
            } else {
                return context.getResources().getString(R.string.not_complete_status);
            }
        }

    public static String getDeadlineForDisplay(Task task, Context context) {
        if (task.getDeadline() == null) {
            return context.getResources().getString(R.string.no_deadline_set);
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

    public static String getPriorityStatusForDisplay(Task task, Context context) {
        if(task.getPriorityStatus() == 0) {
            return context.getResources().getString(R.string.high_priority);
        } else if(task.getPriorityStatus() == 1) {
            return context.getResources().getString(R.string.medium_priority);
        } else if (task.getPriorityStatus() == 2) {
            return context.getResources().getString(R.string.low_priority);
        } else {
            return context.getResources().getString(R.string.no_priority);
        }
    }
}
