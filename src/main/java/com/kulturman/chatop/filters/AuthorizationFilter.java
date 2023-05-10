package com.kulturman.chatop.filters;

import com.kulturman.chatop.services.JwtService;
import com.kulturman.chatop.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    public AuthorizationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String servletPath = request.getServletPath();

        if (
            servletPath.contains("/auth/login") ||
            servletPath.contains("/auth/register")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        if (header != null && header.startsWith("Bearer")) {
            String token = header.split(" ")[1];
            var decodedToken = jwtService.decodeToken(token);

            if (decodedToken != null) {
                UserDetails userDetails = userService.loadUserByUsername(decodedToken.getBody().getSubject());
                var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
