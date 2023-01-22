package ru.os.OnlineShop.controllers.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class UserRequestModel {

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
