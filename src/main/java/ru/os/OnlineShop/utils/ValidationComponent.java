package ru.os.OnlineShop.utils;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.os.OnlineShop.exceptions.UserNotFoundException;
import ru.os.OnlineShop.repositories.UserRepository;
import ru.os.OnlineShop.services.UserService;

/*
* @author ZQR0
* @since 15.01.2023
* This class contains all validation methods needed in UserService.java
*/
@Component
public class ValidationComponent {

    @Autowired
    private UserRepository repository;

    // Here we could use regex and pattern, but I found EmailValidator dep
    public boolean validateEmail(String email) {
        // Got this pattern from : https://www.baeldung.com/java-email-validation-regex
        //final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\\\+_-]+(\\\\.[A-Za-z0-9\\\\+_-]+)*@" + "[^-][A-Za-z0-9\\\\+-]+(\\\\.[A-Za-z0-9\\\\+-]+)*(\\\\.[A-Za-z]{2,})$";
        return EmailValidator.getInstance().isValid(email);
    }

    // This method checks is user already exists by email address
    public boolean userExistsByEmail(String email)  {
        return this.repository.findByEmail(email).isPresent();
    }
}
