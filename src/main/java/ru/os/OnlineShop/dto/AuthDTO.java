package ru.os.OnlineShop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/*
 * @author ZQR0
 * @since 04.01.2023
 * DTO (Data Transfer Object) for Authentication
 * We use Lombok @Data here to create Getters/Setters for fields
 */
@Data
public class AuthDTO extends AbstractDto {

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "password")
    private String password;
}
