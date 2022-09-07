package com.ActivityTrackerWebApp.ToDoWepApp.service;

import com.ActivityTrackerWebApp.ToDoWepApp.dto.TaskDto;
import com.ActivityTrackerWebApp.ToDoWepApp.dto.UserDto;
import com.ActivityTrackerWebApp.ToDoWepApp.model.Task;
import com.ActivityTrackerWebApp.ToDoWepApp.model.User;

import java.util.List;

public interface UserServices {

    User userRegistration(UserDto userDto);

    String userLogin(String email, String password);

    User getUserByEmail(String email);

    Task addTask(TaskDto taskDto);

    Task getTaskById(int id);

    List<Task> viewAllTaskByStatus(String status, int user_id);

    List<Task> viewAllTasks();

    Boolean updateTaskStatus(String status, int id);

    Boolean removeTaskById(int id);

    Task updateTitleAndDescription(TaskDto taskDto, int id);

    String moveForward(int id);

    String moveBackward(int id);

    User getUserById(int id);


    List<Task> showTaskByUser(int id);

}
