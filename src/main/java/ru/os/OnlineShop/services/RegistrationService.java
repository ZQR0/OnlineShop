package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.controllers.models.AuthenticationResponseModel;
import ru.os.OnlineShop.dto.UserDTO;
import ru.os.OnlineShop.entities.RoleEntity;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.exceptions.EmailValidationException;
import ru.os.OnlineShop.exceptions.UserAlreadyExistsException;
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
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    // Can throw 2 exceptions
    public AuthenticationResponseModel register(UserDTO dto) throws UserAlreadyExistsException, EmailValidationException {

        // UserDTO - source class
        // UserEntity - destination class
        UserEntity user = this.mapper.map(dto, UserEntity.class);

        boolean userExists = this.validationComponent.userExistsByEmail(dto.getEmail());
        boolean emailIsValid = this.validationComponent.validateEmail(dto.getEmail());

        if (!userExists) {
            if (emailIsValid) {
                // Saving user with builder
                user.builder()
                        .setEmail(dto.getEmail())
                        .setFirstName(dto.getFirstName())
                        .setPassword(this.passwordEncoder.encode(dto.getPassword())) //TODO : fix
                        .date(this.dateProvider.now())
                        .role(RoleEntity.USER)
                        .isEnabled(true)
                        .build();

                this.repository.save(user);

                log.info("User is registered (Successfully)");

                String generatedToken = this.jwtService.getGeneratedToken(user);

                return AuthenticationResponseModel.builder()
                        .token(generatedToken)
                        .build();

            } else {
                log.info("User is not registered (email validation failed)");
                throw new EmailValidationException("Email validation failed");
            }
        } else {
            log.info("User isn't registered (already exists)");
            throw new UserAlreadyExistsException("User already exists");
        }
    }
}
