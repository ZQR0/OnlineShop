package ru.os.OnlineShop.config;

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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.os.OnlineShop.entities.RoleEntity;
import ru.os.OnlineShop.security.RestAuthEntryPoint;
import ru.os.OnlineShop.security.filters.CookieAuthFilter;
import ru.os.OnlineShop.utils.URLAddressesContainer;

import java.util.logging.Logger;

/*
* @author ZQR0
* @since 14.01.2023
* @version 0.0.2
*/
@Configuration
@EnableWebSecurity
//@PropertySource("classpath:urls.properties")
public class WebSecurityConfig {

    @Autowired
    private RestAuthEntryPoint restAuthEntryPoint;


    @Bean(name = "security_bean")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // TODO: Refactor all code
        http
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(this.restAuthEntryPoint)
                .and()
                .addFilterBefore(new CookieAuthFilter(), BasicAuthenticationFilter.class)

                .formLogin().defaultSuccessUrl("/home", true)
                .loginPage("/login")
                .and()
                .logout(logout -> logout.deleteCookies(CookieAuthFilter.COOKIE_NAME).invalidateHttpSession(true))

                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers(URLAddressesContainer.defaultAPI_URL).permitAll()
                                .requestMatchers(URLAddressesContainer.adminAdvanced_URL).hasRole(RoleEntity.ADMIN.getRoleName())
                                .requestMatchers(URLAddressesContainer.defaultAdminURL).hasRole(RoleEntity.ADMIN.getRoleName())
                                .requestMatchers(URLAddressesContainer.advanced_API_URl).permitAll()
                                .anyRequest().authenticated()
                )

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .invalidSessionUrl("/logout")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);


        Logger.getLogger("Web Security Logger").info("Security works");

        return http.build();
    }

    @Bean(name = "auth_manager_bean")
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
