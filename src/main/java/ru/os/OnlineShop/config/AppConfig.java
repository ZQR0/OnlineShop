package ru.os.OnlineShop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.os.OnlineShop.services.CustomUserDetailsService;

import java.util.logging.Logger;

@Configuration
@Slf4j
public class AppConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean(name = "bcrypt_bean")
    public PasswordEncoder passwordEncoder() {
        Logger.getLogger("PasswordEncoder Logger").info("PasswordEncoder bean created");

        return new BCryptPasswordEncoder();
    }

    @Bean(name = "auth_manager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        Logger.getLogger("Authentication Manager").info("Auth Manager launched");
        return configuration.getAuthenticationManager();
    }

    @Bean(name = "auth_provider")
    public AuthenticationProvider authenticationProvider() {
        // Authentication provider requires a UserDetailsService and PasswordEncoder
        // for ProviderManager class

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());

        Logger.getLogger("Authentication Provider").info("Auth Provider launched");

        return daoAuthenticationProvider;
    }
}
