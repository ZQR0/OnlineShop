package ru.wm.WorkManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.wm.WorkManager.entities.RoleEntity;
import ru.wm.WorkManager.security.RestAuthEntryPoint;

/*
* @author ZQR0
* @since 14.01.2023
* @version 0.0.1
*/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private RestAuthEntryPoint restAuthEntryPoint;

    @Bean(name = "security_bean")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthEntryPoint);

        // Disabling CSRF
        http.csrf().disable();

        return http.build();
    }

    @Bean(name = "auth_bean")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean(name = "in_memory_auth_bean")
    public UserDetailsService inMemoryAuthBean() {
        UserDetails user = User.builder()
                .username("user")
                .password("user1234")
                .roles(RoleEntity.USER.getRoleName())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("admin123")
                .roles(RoleEntity.ADMIN.getRoleName())
                .build();

        UserDetails bannedUser = User.builder()
                .username("banned")
                .password("banned12")
                .roles(RoleEntity.BANNED.getRoleName())
                .build();

        return new InMemoryUserDetailsManager(user, admin, bannedUser);
    }
}
