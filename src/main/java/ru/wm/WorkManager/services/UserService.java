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
import ru.wm.WorkManager.repositories.UserRepository;
import ru.wm.WorkManager.services.interfaces.UserServiceInterface;
import ru.wm.WorkManager.utils.DateProvider;

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

    @Override
    public Optional<UserEntity> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return this.repository.findAll();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username);
    }

    @Override
    public void register(UserDTO dto) throws EmailValidationException {

        if (validateEmail(dto.getEmail())) {
            if (!userExistsByEmail(dto.getEmail())) {
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

    private boolean validateEmail(String email) {
        // Got this pattern from : https://www.baeldung.com/java-email-validation-regex
        //final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\\\+_-]+(\\\\.[A-Za-z0-9\\\\+_-]+)*@" + "[^-][A-Za-z0-9\\\\+-]+(\\\\.[A-Za-z0-9\\\\+-]+)*(\\\\.[A-Za-z]{2,})$";
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean userExistsByEmail(String email) {
        return this.findByEmail(email) != null;
    }
}
