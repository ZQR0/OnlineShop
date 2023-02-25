package ru.os.OnlineShop.models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
@Builder
public class AuthenticationResponseModel implements Serializable {
    private String token;
}
