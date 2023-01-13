package ru.wm.WorkManager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.wm.WorkManager.dto.UserDTO;
import ru.wm.WorkManager.entities.UserEntity;
import ru.wm.WorkManager.exceptions.EmailValidationException;
import ru.wm.WorkManager.exceptions.UserNotFoundException;
import ru.wm.WorkManager.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(
            path = "user/get-all-users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<UserEntity> getAllUsers() {
        return this.service.getAllUsers();
    }

    @GetMapping(
            path = "user/by-id/",
            params = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Optional<UserEntity> getById(@RequestParam(name = "id") Long id) {
        return this.service.findById(id);
    }

    @GetMapping(
            path = "user/by-username/",
            params = {"/{username}"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserEntity findByUsername(@RequestParam(name = "username") String username) {
        return this.service.findByUsername(username);
    }

    @GetMapping(
            path = "user/by-email/",
            params = "/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserEntity findByEmail(@RequestParam(name = "email") String email) {
        return this.service.findByEmail(email);
    }

    @PostMapping(
            path = "user/register/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void registerUser(@RequestBody UserDTO dto) {
        try {
            this.service.register(dto);
        } catch (EmailValidationException ex) {
            ex.printStackTrace();
        }
    }

}
