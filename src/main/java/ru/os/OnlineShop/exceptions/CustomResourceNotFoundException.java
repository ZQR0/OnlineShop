package ru.os.OnlineShop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomResourceNotFoundException extends Exception {

    public CustomResourceNotFoundException(String msg) {
        super(msg);
    }

    public CustomResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
