package ru.os.OnlineShop.security.auth;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/*
* @author ZQR0
* @since 04.02.2023
* This class must create a token for CustomAuthenticationProvider
*/
@Component
@Slf4j
public class CustomAuthenticationToken {

    /*
    * @method createAuthenticationToken
    * @param
    */
    public Authentication createAuthenticationToken(Authentication authentication, UserDetails user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        token.setDetails(authentication.getDetails());

        return token;
    }

}
