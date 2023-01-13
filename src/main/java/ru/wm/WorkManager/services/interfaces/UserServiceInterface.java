package ru.wm.WorkManager.services.interfaces;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.wm.WorkManager.dto.UserDTO;
import ru.wm.WorkManager.entities.UserEntity;
import ru.wm.WorkManager.exceptions.EmailValidationException;
import ru.wm.WorkManager.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;


/*
* @author ZQR0
* @since 04.01.2023
* @version 0.0.1
* This interface contains all method to implement in UserService.java
*/
public interface UserServiceInterface {
    Optional<UserEntity> findById(Long id);
    List<UserEntity> getAllUsers();
    void register(UserDTO dto) throws EmailValidationException;
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username) throws UsernameNotFoundException;
}
