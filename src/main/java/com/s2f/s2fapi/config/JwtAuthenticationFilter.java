package com.s2f.s2fapi.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        String userEmail = extractUsernameFromJwt(jwt, response);
        if (userEmail != null) {
            authenticateUser(userEmail, jwt, request, response);
        }

        filterChain.doFilter(request, response);
    }

    private String extractUsernameFromJwt(String jwt, HttpServletResponse response) {
        try {
            return jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            log.error("JWT Token is expired: {}", e.getMessage());
            sendUnauthorizedError(response, "JWT Token is expired");
            return null;
        } catch (Exception e) {
            log.error("Error extracting username from JWT: {}", e.getMessage());
            sendUnauthorizedError(response, "Invalid token");
            return null;
        }
    }

    private void authenticateUser(
            String userEmail,
            String jwt,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Vérifier si le token est expiré
                    if (jwtService.isTokenExpired(jwt)) {
                        sendUnauthorizedError(response, "Token expired");
                        return;
                    }

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    sendUnauthorizedError(response, "Invalid token");
                }
            } catch (Exception e) {
                log.error("Authentication failed for user: {}: {}", userEmail, e.getMessage());
                sendUnauthorizedError(response, "Authentication failed");
            }
        }
    }

    private void sendUnauthorizedError(HttpServletResponse response, String message) {
        try {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
        } catch (IOException e) {
            log.error("Failed to send error response: {}", e.getMessage());
        }
    }
}
