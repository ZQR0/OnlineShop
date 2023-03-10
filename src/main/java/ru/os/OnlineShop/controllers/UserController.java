package ru.os.OnlineShop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.os.OnlineShop.controllers.handlers.HttpErrorHandler;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.exceptions.UserNotFoundException;
import ru.os.OnlineShop.services.RegistrationService;
import ru.os.OnlineShop.services.UserService;

import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(
            path = "api/user/get-all-users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<UserEntity> getAllUsers() {
        return this.userService.getAllUsers();
    }


    @GetMapping(
            path = "api/user/by-id/",
            params = "id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getById(@RequestParam(name = "id") Long id) {
        try {
            UserEntity user = this.userService.findById(id);
            return new ResponseEntity<>(
                    user, HttpStatus.OK
            );
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "User with id " + id + " not found"
                    ),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @GetMapping(
            path = "api/user/by-first-name/",
            params = "first-name",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByUsername(@RequestParam(name = "first-name") String firstName) {
        try {
            UserEntity user = this.userService.findByFirstName(firstName);
            return new ResponseEntity<>(
                    user, HttpStatus.OK
            );
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "User with first name " + firstName + " not found"
                    ),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @GetMapping(
            path = "api/user/by-email/",
            params = "email",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
   public ResponseEntity<?> getByEmail(@RequestParam(name = "email") String email) {
        try {
            UserEntity user = this.userService.findByEmail(email);
            return new ResponseEntity<>(
                    user, HttpStatus.OK
            );
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "User with email " + email + " not found"
                    ),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @DeleteMapping(
            path = "api/user/delete-by-id",
            params = "id"
    )
    public ResponseEntity<?> deleteUserById(@RequestParam Long id) {
        try {
            String result = this.userService.deleteUserById(id);

            return new ResponseEntity<>(
                    result, HttpStatus.OK
            );
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(
                            HttpStatus.BAD_REQUEST.value(),
                            ""
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
