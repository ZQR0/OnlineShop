package ru.os.OnlineShop.controllers;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.os.OnlineShop.controllers.handlers.HttpErrorHandler;
import ru.os.OnlineShop.controllers.models.LoginRequestModel;
import ru.os.OnlineShop.dto.AuthDTO;
import ru.os.OnlineShop.exceptions.AuthenticationFailedException;
import ru.os.OnlineShop.services.AuthService;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping(path = "api/auth/sign-in/")
    public ResponseEntity<?> signIn(@RequestBody LoginRequestModel requestModel) {
        try {
            AuthDTO dto = this.mapper.map(requestModel, AuthDTO.class);

            UserDetails authUser = this.authService.signIn(dto);

            return new ResponseEntity<>(
                    "User authorized",
                    HttpStatus.OK
            );
        } catch (AuthenticationFailedException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(
                            HttpStatus.UNAUTHORIZED.value(),
                            "Authorization failed"
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PostMapping(path = "api/auth/sign-out")
    public ResponseEntity<?> signOut(@AuthenticationPrincipal AuthDTO dto) {
        this.authService.signOut();

        return ResponseEntity.noContent().build();
    }
}
