package ru.os.OnlineShop.exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
