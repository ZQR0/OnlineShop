package ru.os.OnlineShop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
public class AuthDTO implements Serializable {

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "password")
    private String password;
}
