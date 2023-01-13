package ru.wm.WorkManager.exceptions;

import java.util.HashMap;
import java.util.Map;

/*
* @author ZQR0
* @since 11.01.2023
* @version 0.0.1
* EmailValidationException is for throwing an exception, when input string is not email type
*/
public class EmailValidationException extends Throwable {
    public EmailValidationException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public void printStackTrace() {
        this.getErrorLog();
        super.printStackTrace();
    }

    private void getErrorLog() {
        Map<String, String> message = new HashMap<>();
        message.put("status", "error");
        message.put("message", "email validation failed");
        message.put("tip", "check is '@' character in your input email");

        System.out.println(message);
    }
}
