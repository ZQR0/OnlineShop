package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.dto.UserDTO;
import ru.os.OnlineShop.entities.RoleEntity;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.exceptions.EmailValidationException;
import ru.os.OnlineShop.repositories.UserRepository;
import ru.os.OnlineShop.utils.DateProvider;
import ru.os.OnlineShop.utils.ValidationComponent;

import java.util.logging.Logger;

@Service
@Slf4j
public class RegistrationService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ValidationComponent validationComponent;

    @Autowired
    private DateProvider dateProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(UserDTO dto) throws EmailValidationException {

        // Encoded password with PasswordEncoder
        String encodedPassword = passwordEncoder.encode(dto.getPassword());


        if (this.validationComponent.validateEmail(dto.getEmail())) {
            if (!this.validationComponent.userExistsByEmail(dto.getEmail())) {
                // Saving user with builder
                this.repository.save(
                        UserEntity.builder()
                                .setEmail(dto.getEmail())
                                .setUsername(dto.getUsername())
                                .setPassword(encodedPassword)
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
