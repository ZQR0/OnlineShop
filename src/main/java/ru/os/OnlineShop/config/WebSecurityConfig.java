package ru.os.OnlineShop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import ru.os.OnlineShop.entities.RoleEntity;
import ru.os.OnlineShop.security.RestAuthEntryPoint;
import ru.os.OnlineShop.security.auth.CustomAuthenticationProvider;
import ru.os.OnlineShop.security.filters.CookieAuthFilter;
import ru.os.OnlineShop.security.filters.EmailPasswordAuthFilter;
import ru.os.OnlineShop.services.CustomUserDetailsService;
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
    private CustomAuthenticationProvider provider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


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
                        .invalidSessionUrl("/logout")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                // Filters for HTTP-request (Auth requests basically)
                .addFilterBefore(new EmailPasswordAuthFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new CookieAuthFilter(), EmailPasswordAuthFilter.class)
                .exceptionHandling().authenticationEntryPoint(this.restAuthEntryPoint)
                .and()
                .logout(logout -> logout.deleteCookies(CookieAuthFilter.COOKIE_NAME)
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true))

                // all allowed URL-addresses with their roles
                .authorizeHttpRequests(
                        (auth) -> auth
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

    // Added this to allow only one session in active
    @Bean(name = "http_event_publisher_bean")
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    // Getting auth-manager to make auth-functionality
    @Bean(name = "auth_manager_bean")
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(this.provider);
        builder.userDetailsService(this.customUserDetailsService);
        return builder.build();
    }

    // In this method we create all
    // in memory users
    // For example, we contain ADMIN there
    // TODO: Use .properties file to contain all logins/passwords in there
    @Bean(name = "in_memory_auth_bean")
    public UserDetailsService inMemoryAuthBean() {
        UserDetails user = User.builder()
                .username("user")
                .password("user1234")
                .roles(RoleEntity.USER.name())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("admin123")
                .roles(RoleEntity.ADMIN.name())
                .build();

        Logger.getLogger("In memory authorized fake-users").info("In memory users сreated");

        return new InMemoryUserDetailsManager(user, admin);
    }
}
