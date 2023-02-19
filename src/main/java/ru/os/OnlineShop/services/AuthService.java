package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.controllers.models.AuthenticationResponseModel;
import ru.os.OnlineShop.dto.AuthDTO;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.exceptions.UserNotFoundException;
import ru.os.OnlineShop.services.interfaces.AuthServiceInterface;


@Service
@Slf4j
public class AuthService implements AuthServiceInterface {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthenticationResponseModel signIn(AuthDTO dto) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );

            UserEntity userByEmail = this.userService.findByEmail(dto.getEmail());

            String jwtToken = this.jwtService.getGeneratedToken(userByEmail);

            return AuthenticationResponseModel.builder()
                    .token(jwtToken)
                    .build();
        } catch (UserNotFoundException ex) {
            return AuthenticationResponseModel.builder()
                    .token("No token (credentials are invalid)")
                    .build();
        }
    }

}
