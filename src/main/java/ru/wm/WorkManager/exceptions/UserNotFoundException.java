package ru.wm.WorkManager.exceptions;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public void printStackTrace() {
        StackTraceElement elements[] = this.getStackTrace();
        Gson gson = new Gson();
        Map<String, String> error = new HashMap<>();

        for (StackTraceElement e : elements) {
            error.put("ex", e.toString());
            error.put("message", "user not found by given data");
            error.put("error", Arrays.toString(this.getStackTrace()));
            error.put("tip", "check is given data actually valid");
        }

        System.out.println(gson.toJson(error));
    }
}
