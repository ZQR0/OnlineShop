package ru.os.OnlineShop.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AuthenticationRequestModel {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
