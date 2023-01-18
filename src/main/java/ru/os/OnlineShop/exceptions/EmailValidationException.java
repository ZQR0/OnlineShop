package ru.wm.WorkManager.exceptions;

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
public class EmailValidationException extends Throwable {
    public EmailValidationException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
    public EmailValidationException(String errorMessage) {
        super(errorMessage);
    }

    // Custom implementation of printStackTrace() method to get JSON
    @Override
    public void printStackTrace() {
        StackTraceElement elements[] = this.getStackTrace();
        Gson gson = new Gson();
        Map<String, String> error = new HashMap<>();

        for (StackTraceElement e : elements) {
            error.put("ex", e.toString());
            error.put("message", "email validation failed");
            error.put("error", Arrays.toString(this.getStackTrace()));
            error.put("tip", "check is email field contains '@' char & is this email actually");
        }

        System.out.println(gson.toJson(error));
    }

}
