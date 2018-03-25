package com.example.user.completionist;

/**
 * Created by user on 24/03/2018.
 */

public class Task {
    int id;
//    Easier to call string later on - is there rational for using int?
    String taskTitle, taskDescription;
    Integer completionStatus;

    public Task(int id, String taskTitle, String taskDescription, Integer completionStatus) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.completionStatus = completionStatus;
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

    public boolean getCompletionStatusForCheckBox() {
        if(getCompletionStatus() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
