package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.exceptions.UserNotFoundException;
import ru.os.OnlineShop.repositories.UserRepository;
import ru.os.OnlineShop.services.interfaces.UserServiceInterface;
import ru.os.OnlineShop.utils.DateProvider;
import ru.os.OnlineShop.utils.ValidationComponent;

import java.util.List;

// TODO: update
@Service
@Slf4j
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository repository;

    @Autowired
    private DateProvider dateProvider;

    @Autowired
    private ValidationComponent validationComponent;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity findById(Long id) throws UserNotFoundException {
        return this.repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found")
        );
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return this.repository.findAll();
    }

    @Override
    public UserEntity findByEmail(String email) throws UserNotFoundException {
        return this.repository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email " + email + " not found")
        );
    }

    @Override
    public UserEntity findByFirstName(String firstName) throws UserNotFoundException {
        return this.repository.findByFirstName(firstName).orElseThrow(
                () -> new UserNotFoundException("User with first name " + firstName + " not found")
        );
    }

    public UserEntity validateEmailAndPassword(String email, String password) throws UserNotFoundException {
        return this.repository.findByEmail(email)
                .filter(user -> this.passwordEncoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public String deleteUserById(Long id) throws UserNotFoundException {
        UserEntity userById = this.findById(id);
        this.repository.deleteById(id);

        return "User deleted";
    }

}
