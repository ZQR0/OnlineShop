package ru.os.OnlineShop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.dto.UserDTO;
import ru.os.OnlineShop.exceptions.EmailValidationException;
import ru.os.OnlineShop.exceptions.UserNotFoundException;
import ru.os.OnlineShop.services.UserService;

import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService service;


    @GetMapping(
            path = "api/user/get-all-users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<UserEntity> getAllUsers() {
        return this.service.getAllUsers();
    }


    @GetMapping(
            path = "api/user/by-id/",
            params = "id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getById(@RequestParam(name = "id") Long id) {
        try {
            UserEntity user = this.service.findById(id);
            return new ResponseEntity<>(
                    user, HttpStatus.OK
            );
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(
                    ex, HttpStatus.NOT_FOUND
            );
        }
    }


    @GetMapping(
            path = "api/user/by-username/",
            params = "username",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByUsername(@RequestParam(name = "username") String username) {
        try {
            UserEntity user = this.service.findByUsername(username);
            return new ResponseEntity<>(
                    user, HttpStatus.OK
            );
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(
                    ex, HttpStatus.OK
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
            UserEntity user = this.service.findByEmail(email);
            return new ResponseEntity<>(
                    user, HttpStatus.OK
            );
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(
                    ex, HttpStatus.NOT_FOUND
            );
        }
    }

}
