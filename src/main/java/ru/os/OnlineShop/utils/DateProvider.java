package ru.wm.WorkManager.utils;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/*
* @author ZQR0
* @since 1.01.2023
*/
@Component
public class DateProvider implements Serializable {

    public Date now() {
        return new Date();
    }
}
