package com.ActivityTrackerWebApp.ToDoWepApp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String fullName;
    private String email;
    private String password;


    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
