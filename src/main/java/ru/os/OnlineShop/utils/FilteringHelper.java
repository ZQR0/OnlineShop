package ru.os.OnlineShop.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/*
* @author ZQR0
* This class contains all required methods for JwtAuthenticationFilter.java
*/
@Component
@Slf4j
public class FilteringHelper {

    public final String HEADER = "Authorization";

    /*
    * @returns true if all conditions are not valid
    * (if request contains header & if it starts with bearer)
    */
    public boolean isAuthHeaderValid(@NonNull HttpServletRequest request) {

        final String AUTH_HEADER = request.getHeader(this.HEADER);

        if (AUTH_HEADER == null) {
            log.info("Header is null");
            return false;
        }

        if (!AUTH_HEADER.startsWith("Bearer ")) {
            log.info("Header is not containing bearer string");
            return false;
        }

        return true;
    }
}
