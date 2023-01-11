package ru.wm.WorkManager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.wm.WorkManager.dto.UserDTO;
import ru.wm.WorkManager.entities.UserEntity;
import ru.wm.WorkManager.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(path = "user/get-all-users")
    public List<UserEntity> getAllUsers() {
        return this.service.getAllUsers();
    }

    @GetMapping(path = "user/by-id/", params = "/{id}")
    public Optional<UserEntity> getById(@RequestParam(name = "id") Long id) {
        return this.service.findById(id);
    }

    @GetMapping(path = "user/by-username/", params = {"/{username}"})
    public UserEntity findByUsername(@RequestParam(name = "username") String username) {
        return this.service.findByUsername(username);
    }

    @GetMapping(path = "user/by-email/", params = "/{email}")
    public UserEntity findByEmail(@RequestParam(name = "email") String email) {
        return this.service.findByEmail(email);
    }

}
