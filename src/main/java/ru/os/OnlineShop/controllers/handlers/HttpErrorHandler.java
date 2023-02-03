package ru.os.OnlineShop.controllers.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
* @author ZQR0
* @since 03.02.2023
* This class helps controller to serialize error messages to JSON format
*/
@Data
@AllArgsConstructor
public class HttpErrorHandler {

    private int statusCode;

    private String message;
}
