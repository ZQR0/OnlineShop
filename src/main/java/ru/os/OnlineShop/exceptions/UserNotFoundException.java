package ru.os.OnlineShop.exceptions;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
