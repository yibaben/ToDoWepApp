package com.ActivityTrackerWebApp.ToDoWepApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private String title;
    private String description;

    private int user_id;


    @Override
    public String toString() {
        return "TaskDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
