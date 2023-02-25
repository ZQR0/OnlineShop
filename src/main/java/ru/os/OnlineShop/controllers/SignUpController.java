package ru.os.OnlineShop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.os.OnlineShop.controllers.handlers.HttpErrorHandler;
import ru.os.OnlineShop.models.AuthenticationResponseModel;
import ru.os.OnlineShop.models.UserRequestModel;
import ru.os.OnlineShop.dto.UserDTO;
import ru.os.OnlineShop.exceptions.EmailValidationException;
import ru.os.OnlineShop.exceptions.UserAlreadyExistsException;
import ru.os.OnlineShop.services.RegistrationService;
import ru.os.OnlineShop.services.UserService;

@RestController
@Slf4j
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ModelMapper mapper;

    @RequestMapping(
            path = "api/user/sign-up",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> signUpUser(@RequestBody UserRequestModel requestModel) {
        try {
            UserDTO dto = this.mapper.map(requestModel, UserDTO.class);
            AuthenticationResponseModel token = this.registrationService.register(dto);

            return new ResponseEntity<>(
                    token, HttpStatus.OK
            );
        } catch (UserAlreadyExistsException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(
                            HttpStatus.BAD_REQUEST.value(),
                            "User with this credentials already exists"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        } catch (EmailValidationException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(
                            HttpStatus.BAD_REQUEST.value(),
                            "Email validation failed"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
