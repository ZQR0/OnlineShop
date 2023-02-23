package ru.os.OnlineShop.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.os.OnlineShop.services.JwtService;
import ru.os.OnlineShop.utils.FilteringHelper;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FilteringHelper filteringHelper;

    // TODO: сделать фильтрацию только по определённому url
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

//        final String REQUEST_URL = request.getRequestURL().toString();
//        final String URL = "http://localhost:3030/api/auth/sign-in/";

//        System.out.println(request.getProtocol()); // HTTP 1.0
//        System.out.println(request.getLocalName()); // localhost

        final String AUTH_HEADER = request.getHeader("Authorization");
        final String JWT;
        final String USER_EMAIL;

        if (!this.filteringHelper.isAuthHeaderValid(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        JWT = AUTH_HEADER.substring(7);
        USER_EMAIL = this.jwtService.extractUsername(JWT);

        if (USER_EMAIL != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(USER_EMAIL);

            if (this.jwtService.isTokenValid(JWT, userDetails)) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            if (this.jwtService.isTokenExpired(JWT)) {
                log.info("Token is expired");
            }
        }

        filterChain.doFilter(request, response);
    }
}
