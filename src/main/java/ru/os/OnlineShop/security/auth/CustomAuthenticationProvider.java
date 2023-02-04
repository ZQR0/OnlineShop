package ru.os.OnlineShop.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import ru.os.OnlineShop.exceptions.AuthenticationFailedException;
import ru.os.OnlineShop.services.AuthService;
import ru.os.OnlineShop.services.CustomUserDetailsService;
import ru.os.OnlineShop.services.UserService;

import java.util.Optional;
import java.util.logging.Logger;


@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationToken customAuthenticationToken;

    // TODO: make it more advanced
    @Override
    public Authentication authenticate(Authentication authentication) {
        final String email = authentication.getName();

        UserDetails user = null;

        try {
            user = this.userDetailsService.loadUserByUsername(email);
        } catch (UsernameNotFoundException ex) {
            Logger.getLogger("CustomAuthProvider logger").info("User name not found");
        }

        return this.customAuthenticationToken.createAuthenticationToken(authentication, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
