package ru.os.OnlineShop.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/*
 * @author ZQR0
 * @since 28.01.2023
 * We need this component to filter all input cookies
 * and check if cookie already exists in our system
 *
 * Using @Slf4j for logging
 */
@Component
@Slf4j
@PropertySource("classpath:application.properties")
public class CookieAuthFilter extends OncePerRequestFilter {

    @Value(value = "${cookie.cookie-name}")
    public static String COOKIE_NAME;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<Cookie> authCookie = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                .findFirst();

        authCookie.ifPresent(cookie -> SecurityContextHolder.getContext().setAuthentication(
                new PreAuthenticatedAuthenticationToken(cookie.getValue(), null))
        );

        filterChain.doFilter(request, response);
    }
}
