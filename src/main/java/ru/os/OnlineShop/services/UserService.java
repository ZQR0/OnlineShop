package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.exceptions.UserNotFoundException;
import ru.os.OnlineShop.repositories.UserRepository;
import ru.os.OnlineShop.services.interfaces.UserServiceInterface;
import ru.os.OnlineShop.utils.DateProvider;
import ru.os.OnlineShop.utils.ValidationComponent;

import java.util.List;

@Service
@Slf4j
@Transactional
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DateProvider dateProvider;

    @Autowired
    private ValidationComponent validationComponent;

    @Override
    public UserEntity findById(Long id) throws UserNotFoundException {
        return this.repository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User with id " + id + " not found")
        );
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return this.repository.findAll();
    }

    @Override
    public UserEntity findByEmail(String email) throws UserNotFoundException {
        return this.repository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User with email " + email + " not found")
        );
    }

    @Override
    public UserEntity findByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username " + username + " not found")
        );
    }
}
