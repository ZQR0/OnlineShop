package ru.os.OnlineShop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@Configuration
@Slf4j
public class PasswordEncoderConfig {

    @Bean(name = "bcrypt_bean")
    @Scope(value = "singleton")
    public PasswordEncoder passwordEncoder() {
        Logger.getLogger("PasswordEncoder Logger").info("PasswordEncoder bean created");

        return new BCryptPasswordEncoder();
    }
}
