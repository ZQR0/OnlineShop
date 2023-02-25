package ru.os.OnlineShop.exceptions;

import com.google.gson.Gson;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
* @author ZQR0
* @since 11.01.2023
* @version 0.0.1
* EmailValidationException is for throwing an exception, when input string is not email type
*/
public class EmailValidationException extends Exception {
    public EmailValidationException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
    public EmailValidationException(String errorMessage) {
        super(errorMessage);
    }
}
