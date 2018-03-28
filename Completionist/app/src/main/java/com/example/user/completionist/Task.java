package com.example.user.completionist;

import java.io.Serializable;

/**
 * Created by user on 24/03/2018.
 */

public class Task implements Serializable {
    int id;
//    Easier to call string later on - is there rational for using int?
    private String taskTitle, taskDescription, deadline;
    private Integer completionStatus, priorityStatus;

    public Task(int id, String taskTitle, String taskDescription, Integer completionStatus, Integer priorityStatus, String deadline) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.completionStatus = completionStatus;
        this.priorityStatus = priorityStatus;
        this.deadline = deadline;
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

    public Integer getPriorityStatus() { return this.priorityStatus; }

    public String getDeadline() {return this.deadline; }

    public boolean getCompletionStatusForCheckBox() {
        if (getCompletionStatus() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
