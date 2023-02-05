package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.dto.AuthDTO;
import ru.os.OnlineShop.exceptions.AuthenticationFailedException;
import ru.os.OnlineShop.security.auth.CustomAuthenticationToken;
import ru.os.OnlineShop.services.interfaces.AuthServiceInterface;
import ru.os.OnlineShop.utils.ValidationComponent;

import java.util.logging.Logger;

/*
* @author ZQR0
* @since 03.02.2023
* Auth method should validate input email & password
*/
@Service
@Slf4j
public class AuthService implements AuthServiceInterface {

    @Autowired
    private ValidationComponent validationComponent;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public UserDetails signIn(AuthDTO dto) throws AuthenticationFailedException {
        final String email = dto.getEmail();
        if (this.validationComponent.userExistsByEmail(email)) {
            Logger.getLogger("AuthService Logger").info("Authentication transfer to CustomUserDetails");
            return this.userDetailsService.loadUserByUsername(email);
        }

        Logger.getLogger("AuthService Logger").info("Email is not valid");
        throw new AuthenticationFailedException("Authentication failed");
    }

    @Override
    public void signOut() {
        SecurityContextHolder.clearContext();
    }

}
