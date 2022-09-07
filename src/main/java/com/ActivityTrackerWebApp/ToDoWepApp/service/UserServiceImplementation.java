package com.ActivityTrackerWebApp.ToDoWepApp.service;


import com.ActivityTrackerWebApp.ToDoWepApp.dto.TaskDto;
import com.ActivityTrackerWebApp.ToDoWepApp.dto.UserDto;
import com.ActivityTrackerWebApp.ToDoWepApp.exceptionHandling.TaskNotFound;
import com.ActivityTrackerWebApp.ToDoWepApp.exceptionHandling.UserNotFound;
import com.ActivityTrackerWebApp.ToDoWepApp.model.Status;
import com.ActivityTrackerWebApp.ToDoWepApp.model.Task;
import com.ActivityTrackerWebApp.ToDoWepApp.model.User;
import com.ActivityTrackerWebApp.ToDoWepApp.repository.TaskRepository;
import com.ActivityTrackerWebApp.ToDoWepApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//@RequiredArgsConstructor
//@AllArgsConstructor
@Service
public class UserServiceImplementation implements UserServices{

    UserRepository userRepository;

    TaskRepository taskRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public User userRegistration(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return userRepository.save(user);
    }

    @Override
    public String userLogin(String email, String password) {
        String notification = "";
        User user = getUserByEmail(email);
        if(user.getPassword().equals(password)){
            notification = "Login Successful";
        }else {
            notification = "Sorry! Incorrect Password";
        }
        return notification;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFound(email + "Incorrect or Not Found"));
    }

    @Override
    public Task addTask(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        User loggedUser =  getUserById(taskDto.getUser_id());
        task.setUser(loggedUser);
        task.setStatus(Status.PENDING);
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFound("Task Not Found or Not Added"));
    }

    @Override
    public List<Task> viewAllTaskByStatus(String status, int user_id) {
        return taskRepository.viewTasksByStatus(status, user_id);
    }

    @Override
    public List<Task> viewAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Boolean updateTaskStatus(String status, int id) {
        return taskRepository.editTasksByIdAndStatus(status, id);
    }

    @Override
    public Boolean removeTaskById(int id) {
        taskRepository.deleteById(id);
        return true;
    }

    @Override
    public Task updateTitleAndDescription(TaskDto taskDto, int id) {
        Task task = getTaskById(id);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        return taskRepository.save(task);
    }

    @Override
    public String moveForward(int id) {
        String message ="";
        Task task = taskRepository.findById(id).get();
        if(task.getStatus() == Status.PENDING){
            task.setStatus(Status.IN_PROGRESS);
            taskRepository.save(task);
            message="moved from pending to in-progress";
        }else if (task.getStatus() == Status.IN_PROGRESS){
            task.setStatus(Status.COMPLETED);
            task.setCompletedAt(LocalDateTime.now());
            taskRepository.save(task);
            message = "moved from in-progress to completed";
        }else {
            message ="unauthorized moved";
        }
        return message;
    }

    @Override
    public String moveBackward(int id) {
        String message ="";
        Task task = taskRepository.findById(id).get();
        if(task.getStatus() == Status.IN_PROGRESS){
            task.setStatus(Status.PENDING);
            taskRepository.save(task);
            message ="moved back to pending";
        }else{
            message = " Unauthorized";
        }
        return message;
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("This user was not found"));
    }

    @Override
    public List<Task> showTaskByUser(int id) {
        return  taskRepository.listOfTasksByUserId(id);
    }
}
