package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.dto.UserDTO;
import ru.os.OnlineShop.entities.RoleEntity;
import ru.os.OnlineShop.entities.UserEntity;
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


    public void register(UserDTO dto) throws UserAlreadyExistsException {

        // UserDTO - source class
        // UserEntity - destination class
        UserEntity user = this.mapper.map(dto, UserEntity.class);

        boolean userExists = this.validationComponent.userExistsByEmail(dto.getEmail());

        if (!userExists) {
            // Saving user with builder
            user.builder()
                    .setEmail(dto.getEmail())
                    .setUsername(dto.getUsername())
                    .setPassword(dto.getPassword())
                    .date(this.dateProvider.now())
                    .role(RoleEntity.USER.getRoleName())// TODO: Role definition
                    .isEnabled(true)
                    .build();

            this.repository.save(user);

            Logger.getLogger("User registration").info("User is registered");
        } else {
            Logger.getLogger("User registration").info("User isn't registered");
            throw new UserAlreadyExistsException("User already exists");
        }
    }
}
