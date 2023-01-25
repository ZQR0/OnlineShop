package ru.os.OnlineShop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
import ru.os.OnlineShop.entities.RoleEntity;
import ru.os.OnlineShop.security.RestAuthEntryPoint;

import java.util.logging.Logger;

/*
* @author ZQR0
* @since 14.01.2023
* @version 0.0.2
*/
@Configuration
@EnableWebSecurity
@PropertySource("classpath:urls.properties")
public class WebSecurityConfig {

    @Autowired
    private RestAuthEntryPoint restAuthEntryPoint;

    @Value(value = "${url.api.default}")
    private String defaultAPI_URL;

    @Value(value = "${url.api.admin.default}")
    private String defaultAdminURL;

    @Value(value = "${url.api.advanced}")
    private String adminAdvanced_URL;

    @Value(value = "${url.api.advanced}")
    private String advanced_API_URl;

    @Bean(name = "security_bean")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //TODO: Create .env file and contain all constant values in there
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthEntryPoint).and()
                        .authorizeHttpRequests(
                                (auth) -> auth
                                        .requestMatchers(defaultAPI_URL).permitAll()
                                        .requestMatchers(adminAdvanced_URL).hasRole(RoleEntity.ADMIN.getRoleName())
                                        .requestMatchers(defaultAdminURL).hasRole(RoleEntity.ADMIN.getRoleName())
                                        .requestMatchers(advanced_API_URl).permitAll()
                        );

        // Disabling CSRF
        http.csrf().disable();

        Logger.getLogger("Web Security Logger").info("Security works");

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

        Logger.getLogger("In memory authorized fake-users").info("In memory users сreated");

        return new InMemoryUserDetailsManager(user, admin, bannedUser);
    }
}
