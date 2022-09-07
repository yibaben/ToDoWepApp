package com.ActivityTrackerWebApp.ToDoWepApp.controller;

import com.ActivityTrackerWebApp.ToDoWepApp.dto.TaskDto;
import com.ActivityTrackerWebApp.ToDoWepApp.dto.UserDto;
import com.ActivityTrackerWebApp.ToDoWepApp.model.Status;
import com.ActivityTrackerWebApp.ToDoWepApp.model.Task;
import com.ActivityTrackerWebApp.ToDoWepApp.model.User;
import com.ActivityTrackerWebApp.ToDoWepApp.service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller
//@RequestMapping(value = "/user")
public class UserTaskController {

    public final UserServices userServices;

    @Autowired
    public UserTaskController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping(value ="/")
    public String displayLoginPage(Model model){
        model.addAttribute("userDetails" , new UserDto());
        return "login";
    }

    @PostMapping(value = "/loginUser")
    public String loginUser(@RequestParam String email , @RequestParam String password , HttpSession session , Model model){
        String message =  userServices.userLogin(email ,  password);
        if(message.equals("Login Successful")){
            User user = userServices.getUserByEmail(email);
            session.setAttribute("email" , user.getEmail());
            session.setAttribute("id" , user.getId());
            session.setAttribute("name" , user.getFullName());
            return "redirect:/dashboard";
        }else{
            model.addAttribute("errorMessage" , message);
            return  "redirect:/";
        }
    }

    @GetMapping(value = "/register")
    public  String showRegistrationForm(Model model){
        model.addAttribute("userRegistrationDetails" , new UserDto());
        return  "register";
    }

    @PostMapping(value = "/userRegistration")
    public String registration(@ModelAttribute UserDto userDto){
        User registeredUser = userServices.userRegistration(userDto);
        if (registeredUser != null){
            return "redirect:/";
        }else {
            return "redirect:/register";
        }
    }

    @GetMapping("/dashboard")
    public String index(Model model, HttpSession session) {
//        List<Task> allTasks = userServices.viewAllTasks();
//        model.addAttribute("tasks" , allTasks);
//        session.setAttribute("tasks", allTasks);
        if(session.getAttribute("id") == null){
            return "redirect:/";
        }else {
            List<Task> allTasks = userServices.showTaskByUser((Integer) session.getAttribute("id"));
            model.addAttribute("tasks", allTasks);
            session.setAttribute("tasks", allTasks);
            model.addAttribute("today", Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek());
            return "dashboard";
        }
    }

//    @GetMapping(value = "/task/{status}")
//    public String taskByStatus(@PathVariable(name = "status") String status , Model model){
//        List<Task> listOfTaskByStatus = userServices.viewAllTaskByStatus(status);
//        model.addAttribute("tasksByStatus" , listOfTaskByStatus);
//        return "task-by-status";
//    }

    @GetMapping("/delete/{id}")
    public String removeTask(@PathVariable(name = "id") Integer id){
        userServices.removeTaskById(id);
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/editPage/{id}")
    public String showEditPage(@PathVariable(name = "id") Integer id , Model model){
        Task task = userServices.getTaskById(id);
        model.addAttribute("singleTask" , task);
        model.addAttribute("taskBody", new TaskDto());
        return  "editTask";
    }

    @PostMapping(value = "/edit/{id}")
    public String editTask(@PathVariable( name = "id") Integer id , @ModelAttribute TaskDto taskDto){
        userServices.updateTitleAndDescription(taskDto , id);
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/addNewTask")
    public String addTask(Model model){
        model.addAttribute("newTask" , new TaskDto());
        return "createTask";
    }

    @PostMapping(value = "/addTask")
    public String CreateTask(@ModelAttribute TaskDto taskDto){
        userServices.addTask(taskDto);
        return "redirect:/dashboard";
    }

    @GetMapping(value = "/arrow-right/{id}")
    public String moveStatusForward(@PathVariable(name = "id") int id){
        userServices.moveForward(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/arrow-left/{id}")
    public String moveStatusBackward(@PathVariable(name = "id") int id){
        userServices.moveBackward(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/viewTask/{id}")
    public String viewSingleTask(@PathVariable(name = "id") int id, Model model, HttpSession session){
        Task task = userServices.getTaskById(id);
        model.addAttribute("singleTask", task);
        session.setAttribute("singleTask", task);
        return "viewSingleTask";
    }
    @GetMapping("/task/{status}")
    public String viewTaskByStatus(@PathVariable(name = "status")  String status  , Model model, HttpSession session){
         List<Task> tasks = userServices.viewAllTaskByStatus(status, (Integer) session.getAttribute("id"));
        model.addAttribute("task" , tasks);
         return "taskstatus";
    }

}
