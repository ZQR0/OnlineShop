package ru.os.OnlineShop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/*
* @author ZQR0
* @since 04.01.2023
* DTO (Data Transfer Object) for User Service
* We use Lombok @Data here to create Getters/Setters for fields
*/
@Data
public class UserDTO implements Serializable {
    @JsonProperty("email")
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("password")
    private String password;
}
