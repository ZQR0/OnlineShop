package ru.os.OnlineShop.exceptions;

public class AuthenticationFailedException extends Exception {

    public AuthenticationFailedException(String msg) {
        super(msg);
    }

    public AuthenticationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
