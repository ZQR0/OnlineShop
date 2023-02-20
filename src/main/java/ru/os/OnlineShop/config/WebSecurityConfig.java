package ru.os.OnlineShop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.os.OnlineShop.entities.RoleEntity;
import ru.os.OnlineShop.security.RestAuthEntryPoint;
import ru.os.OnlineShop.security.filters.JwtAuthenticationFilter;
import ru.os.OnlineShop.utils.URLAddressesContainer;

import java.util.logging.Logger;

/*
* @author ZQR0
* @since 14.01.2023
*/
@Configuration
@EnableWebSecurity
//@PropertySource("classpath:urls.properties")
public class WebSecurityConfig {

    @Autowired
    private RestAuthEntryPoint restAuthEntryPoint;

    @Autowired
    private URLAddressesContainer container;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // Filter chain security config
    @Bean(name = "security_bean")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // TODO: Refactor all code
        http
                // disabling CSRF-protection
                .csrf().disable()
                // Session management configuration
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling().authenticationEntryPoint(this.restAuthEntryPoint)
                .and()
                .authenticationProvider(this.authenticationProvider)
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // all allowed URL-addresses with their roles
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(container.defaultAPI_URL).permitAll()
                                .requestMatchers(container.adminAdvanced_URL).hasRole(RoleEntity.ADMIN.name())
                                .requestMatchers(container.defaultAdminURL).hasRole(RoleEntity.ADMIN.name())
                                .requestMatchers(container.advanced_API_URl).permitAll()
                                .requestMatchers(container.signInAuthUrl).permitAll()
                                .anyRequest().authenticated()
                );

        Logger.getLogger("Web Security Logger").info("Security works");

        return http.build();
    }

}
