package ru.os.OnlineShop.services.interfaces;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.exceptions.EmailValidationException;
import ru.os.OnlineShop.exceptions.UserNotFoundException;
import ru.os.OnlineShop.dto.UserDTO;

import java.util.List;
import java.util.Optional;

/*
* @author ZQR0
* @since 04.01.2023
* @version 0.0.1
* This interface contains all method to implement in UserService.java
*/
public interface UserServiceInterface {
    UserEntity findById(Long id) throws UserNotFoundException;
    List<UserEntity> getAllUsers();
    void register(UserDTO dto) throws EmailValidationException;
    UserEntity findByEmail(String email) throws UserNotFoundException;
    UserEntity findByUsername(String username) throws UsernameNotFoundException;
}
