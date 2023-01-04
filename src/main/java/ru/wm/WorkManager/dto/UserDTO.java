package ru.wm.WorkManager.dto;

import lombok.Data;

import java.util.Date;

/*
* @author ZQR0
* @since 04.01.2023
* DTO (Data Transfer Object) for User Service
* We use Lombok @Data here to create Getters/Setters for fields
*/
@Data
public class UserDTO {
    private String email;
    private String username;
    private String password;
}
