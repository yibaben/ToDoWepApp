package com.ActivityTrackerWebApp.ToDoWepApp.exceptionHandling;

public class TaskNotFound extends RuntimeException{
    public TaskNotFound(String message){
        super(message);
    }
}
