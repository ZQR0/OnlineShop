package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.entities.UserEntity;
import ru.os.OnlineShop.exceptions.UserNotFoundException;
import ru.os.OnlineShop.repositories.UserRepository;

import java.util.logging.Logger;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Logger.getLogger("CustomUserDetailsService Logger").info("Started to find by email");
            UserEntity user = this.userService.findByEmail(email);
            Logger.getLogger("CustomUserDetailsService Logger").info("User found by email");

            Logger.getLogger("CustomUserDetailsService Logger").info("Authenticated");

            return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
        } catch (UserNotFoundException ex) {
            Logger.getLogger("CustomUserDetailsService Logger").info("User not found");
        }

        return null;
    }
}
