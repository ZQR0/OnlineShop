package ru.os.OnlineShop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
* @author ZQR0
* @since 02.01.2023
* Password encoder config for create a cryptography operations
*/

@Configuration
public class PasswordEncoderConfig {
    @Bean(name = "bcrypt_bean")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}