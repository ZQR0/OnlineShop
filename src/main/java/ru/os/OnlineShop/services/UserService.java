package ru.wm.WorkManager.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wm.WorkManager.dto.UserDTO;
import ru.wm.WorkManager.entities.RoleEntity;
import ru.wm.WorkManager.entities.UserEntity;
import ru.wm.WorkManager.exceptions.EmailValidationException;
import ru.wm.WorkManager.exceptions.UserNotFoundException;
import ru.wm.WorkManager.repositories.UserRepository;
import ru.wm.WorkManager.services.interfaces.UserServiceInterface;
import ru.wm.WorkManager.utils.DateProvider;
import ru.wm.WorkManager.utils.ValidationComponent;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
    public Optional<UserEntity> findById(Long id) throws UserNotFoundException {
        Optional<UserEntity> user = this.repository.findById(id);
        if (user.isPresent()) return user;

        throw new UserNotFoundException("User not found");
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return this.repository.findAll();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) throws UserNotFoundException {
        Optional<UserEntity> user = this.repository.findByEmail(email);
        if (user.isPresent()) return user;

        throw new UserNotFoundException("User not found");
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = this.repository.findByUsername(username);
        if (user.isPresent()) return user;

        throw new UsernameNotFoundException("Username not found");
    }

    @Override
    public void register(UserDTO dto) throws EmailValidationException {

        if (this.validationComponent.validateEmail(dto.getEmail())) {
            if (!this.validationComponent.userExistsByEmail(dto.getEmail())) {
                // Saving user with builder
                this.repository.save(
                        UserEntity.builder()
                                .setEmail(dto.getEmail())
                                .setUsername(dto.getUsername())
                                .setPassword(dto.getPassword())
                                .date(this.dateProvider.now())
                                .role(RoleEntity.USER.getRoleName())
                                .build()
                );
            }

            Logger.getLogger("registerUser").info("User is registered");
        }

        Logger.getLogger("registerUser").info("User isn't registered");

        throw new EmailValidationException("Email validation failed");
    }
}
