package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.models.AuthenticationResponseModel;
import ru.os.OnlineShop.dto.AuthDTO;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.exceptions.UserNotFoundException;
import ru.os.OnlineShop.repositories.UserRepository;
import ru.os.OnlineShop.services.interfaces.AuthServiceInterface;


@Service
@Slf4j
public class AuthService implements AuthServiceInterface {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public AuthenticationResponseModel signIn(AuthDTO dto) {
        try {

            UserEntity user = this.userService.validateEmailAndPassword(dto.getEmail(), dto.getPassword());

            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );

            return AuthenticationResponseModel.builder()
                    .token("no token")
                    .build();

        } catch (UserNotFoundException ex) {
            return AuthenticationResponseModel.builder()
                    .token("No token (credentials are invalid)")
                    .build();
        }
    }

}
