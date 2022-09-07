package com.ActivityTrackerWebApp.ToDoWepApp.repository;

import com.ActivityTrackerWebApp.ToDoWepApp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT * FROM TASKS WHERE status = ?1 AND user_id = ?2", nativeQuery = true )
    List<Task> viewTasksByStatus(String status, int user_id);


    @Query(value = "UPDATE TASKS SET status = ?1 WHERE id = ?2", nativeQuery = true)
    boolean editTasksByIdAndStatus(String status, int id);

    @Query(value = "SELECT * FROM TASKS WHERE user_id = ?1", nativeQuery = true)
    List<Task> listOfTasksByUserId(int id);
}
