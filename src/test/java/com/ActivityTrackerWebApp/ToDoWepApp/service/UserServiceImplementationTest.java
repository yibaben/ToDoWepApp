package com.ActivityTrackerWebApp.ToDoWepApp.service;

import com.ActivityTrackerWebApp.ToDoWepApp.dto.TaskDto;
import com.ActivityTrackerWebApp.ToDoWepApp.dto.UserDto;
import com.ActivityTrackerWebApp.ToDoWepApp.model.Status;
import com.ActivityTrackerWebApp.ToDoWepApp.model.Task;
import com.ActivityTrackerWebApp.ToDoWepApp.model.User;
import com.ActivityTrackerWebApp.ToDoWepApp.repository.TaskRepository;
import com.ActivityTrackerWebApp.ToDoWepApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplementationTest {

    @Mock
    UserRepository userRepository;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    UserServiceImplementation userServiceImplementation;

    private TaskDto taskDto;
    private UserDto userDto;
    private User user;
    private Task task;
    private Task task2;
    private Task task3;
    private LocalDateTime time;
    List<Task> taskList;

    @BeforeEach
    void setUp() {
        time = LocalDateTime.of(2022, Month.SEPTEMBER, 2, 12, 40, 20, 3000);
        taskList = new ArrayList<>();
        taskList.add(task);
        user = new User(1, "Bennett", "yibaben@gmail.com", "1234", taskList);
        task = new Task(1, "task1", "learn spring MVC", Status.PENDING, time, time, time, user);
        task2 = new Task(2, "task2", "learn spring boot", Status.IN_PROGRESS, time, time, time, user);
        task3 = new Task(3, "task3", "learn spring security", Status.COMPLETED, time, time, time, user);
        taskDto = new TaskDto("Coding", "code for life", 2);
        userDto = new UserDto("Bennett", "yibaben@gmail.com", "1234");
        when(userRepository.save(user)).thenReturn(user);
        when(taskRepository.save(task)).thenReturn(task);
        when(userRepository.findUserByEmail("yibaben@gmail.com")).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.findAll()).thenReturn(taskList);
        when(taskRepository.viewTasksByStatus("PENDING", 1)).thenReturn(taskList);
        when(taskRepository.viewTasksByStatus("IN_PROGRESS", 2)).thenReturn(taskList);
        when(taskRepository.viewTasksByStatus("COMPLETED", 3)).thenReturn(taskList);
    }

    @Test
    void userRegistration() {
        when(userServiceImplementation.userRegistration(userDto)).thenReturn(user);
        var actual = userServiceImplementation.userRegistration(userDto);
        var expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void userLogin() {
        String notification = "Login Successful";
        assertEquals(notification, userServiceImplementation.userLogin("yibaben@gmail.com", "1234"));
    }

    @Test
    void getUserByEmail() {
        var actual = userServiceImplementation.getUserByEmail("yibaben@gmail.com");
        var expected = user;
        assertEquals(expected, actual);
    }

//    @Test
//    void addTask() {
//        when(userServiceImplementation.addTask(taskDto)).thenReturn(task);
//        var actual= userServiceImplementation.addTask(taskDto);
//        var expected = task;
//        assertEquals(expected, actual);
////        assertEquals(task, userServiceImplementation.addTask(taskDto));
//    }

    @Test
    void getTaskById() {
        var actual = userServiceImplementation.getTaskById(1);
        var expected = task;
        assertEquals(expected, actual);
    }

    @Test
    void viewAllTaskByStatus() {
        var actual = userServiceImplementation.viewAllTaskByStatus("Pending", 1);
        var expected = new ArrayList<>();
        assertEquals(expected, actual);
//        assertEquals(taskList , userServiceImplementation.viewAllTaskByStatus("pending", 1));
    }

    @Test
    void viewAllTasks() {
        assertEquals(1 , userServiceImplementation.viewAllTasks().size());
    }

    @Test
    void updateTaskStatus() {
        when (userServiceImplementation.updateTaskStatus("Done", 1)).thenReturn(true);
        var actual = userServiceImplementation.updateTaskStatus("Done", 1);
        var expected = true;
        assertEquals(expected, actual);
//        assertTrue(userServiceImplementation.updateTaskStatus("ongoing" , 1));
    }

    @Test
    void removeTaskById() {
        var actual = userServiceImplementation.removeTaskById(1);
        var expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void updateTitleAndDescription() {
        when (userServiceImplementation.updateTitleAndDescription(taskDto, 1)).thenReturn(task);
        var actual = userServiceImplementation.updateTitleAndDescription(taskDto, 1);
        var expected = task;
        assertEquals(expected, actual);
//        assertEquals(task , userServiceImplementation.updateTitleAndDescription(taskDto, 1));
    }

//    @Test
//    void moveForward() {
//        String   message ="moved from in-progress to completed";
//        assertEquals(message, userServiceImplementation.moveForward(1));
//    }
//
//    @Test
//    void moveBackward() {
//        String message = "moved back to pending";
//        assertEquals(message, userServiceImplementation.moveBackward(1));
//    }

    @Test
    void getUserById() {
        var actual= userServiceImplementation.getUserById(1);
        var expected = user;
        assertEquals(expected, actual);
    }

//    @Test
//    void showTaskByUser() {
//    }
}