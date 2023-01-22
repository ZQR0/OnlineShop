package ru.os.OnlineShop.exceptions;

public class UserAlreadyExistsException extends Throwable {

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }

    public UserAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
