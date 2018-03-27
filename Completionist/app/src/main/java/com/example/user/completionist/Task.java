package com.example.user.completionist;

import java.io.Serializable;

/**
 * Created by user on 24/03/2018.
 */

public class Task implements Serializable {
    int id;
//    Easier to call string later on - is there rational for using int?
    private String taskTitle, taskDescription, priorityStatus;
    private Integer completionStatus;

    public Task(int id, String taskTitle, String taskDescription, Integer completionStatus, String priorityStatus) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.completionStatus = completionStatus;
        this.priorityStatus = priorityStatus;
    }

    public int getId() {
        return this.id;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public Integer getCompletionStatus() {
        return this.completionStatus;
    }

    public String getPriorityStatus() { return this.priorityStatus; }

    public boolean getCompletionStatusForCheckBox() {
        if (getCompletionStatus() == 0) {
            return false;
        } else {
            return true;
        }
    }

//    public void flipCheckBox() {
//        this.completionStatus = (this.completionStatus == 0) ? 1 : 0;
//    }
}
