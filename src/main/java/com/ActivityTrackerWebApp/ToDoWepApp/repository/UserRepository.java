package com.ActivityTrackerWebApp.ToDoWepApp.repository;

import com.ActivityTrackerWebApp.ToDoWepApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM USERS WHERE email = ?1", nativeQuery = true)
    Optional<User> findUserByEmail(String email);
}
