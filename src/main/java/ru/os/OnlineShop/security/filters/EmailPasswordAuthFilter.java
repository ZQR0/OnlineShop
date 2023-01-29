package ru.os.OnlineShop.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.os.OnlineShop.dto.AuthDTO;

import java.io.IOException;

public class EmailPasswordAuthFilter extends OncePerRequestFilter {

    @Autowired
    private ModelMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if ("api/auth-sign-in".equals(request.getServletPath()) && HttpMethod.POST.matches(request.getMethod())) {

            AuthDTO dto = mapper.map(request.getInputStream(), AuthDTO.class);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
        }

        filterChain.doFilter(request, response);
    }
}
