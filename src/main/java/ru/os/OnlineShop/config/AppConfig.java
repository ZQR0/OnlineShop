package ru.os.OnlineShop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.os.OnlineShop.repositories.UserRepository;

@Configuration
@Slf4j
public class AppConfig {

    @Autowired
    private UserRepository repository;

    @Bean(name = "password_encoder_bean")
    public PasswordEncoder passwordEncoder() {
        log.info("PasswordEncoder bean created");
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "user_details_service_bean")
    public UserDetailsService userDetailsService() {
        return username -> this.repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean(name = "auth_manager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        log.info("Auth Manager launched");
        return configuration.getAuthenticationManager();
    }

    @Bean(name = "auth_provider")
    public AuthenticationProvider authenticationProvider() {
        // Authentication provider requires a UserDetailsService and PasswordEncoder
        // for ProviderManager class

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());

        log.info("Auth Provider launched");

        return daoAuthenticationProvider;
    }
}
