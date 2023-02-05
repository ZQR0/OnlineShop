package ru.os.OnlineShop.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
import ru.os.OnlineShop.security.filters.CookieAuthFilter;
import ru.os.OnlineShop.services.AuthService;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping(path = "api/auth/sign-in/")
    public ResponseEntity<?> signIn(
            @AuthenticationPrincipal
            @RequestBody
            LoginRequestModel requestModel,
            HttpServletResponse response) {
        try {
            AuthDTO dto = this.mapper.map(requestModel, AuthDTO.class);

            Cookie authCookie = new Cookie(CookieAuthFilter.COOKIE_NAME, this.authService.createCookieToken(dto));
            authCookie.setSecure(true);
            authCookie.setHttpOnly(true);
            authCookie.setPath("/");

            UserDetails authUser = this.authService.signIn(dto);

            response.addCookie(authCookie);

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

        return new ResponseEntity<>(
                "Logged out",
                HttpStatus.OK
        );
    }
}
