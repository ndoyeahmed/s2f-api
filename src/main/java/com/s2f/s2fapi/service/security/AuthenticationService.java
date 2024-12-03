package com.s2f.s2fapi.service.security;

import com.s2f.s2fapi.config.JwtService;
import com.s2f.s2fapi.constants.ErrorsMessages;
import com.s2f.s2fapi.dto.request.AuthenticationRequest;
import com.s2f.s2fapi.dto.request.RegisterRequest;
import com.s2f.s2fapi.dto.response.AuthenticationResponse;
import com.s2f.s2fapi.dto.response.UserConnectDto;
import com.s2f.s2fapi.exceptions.BadRequestException;
import com.s2f.s2fapi.exceptions.EntityNotFoundException;
import com.s2f.s2fapi.model.Role;
import com.s2f.s2fapi.model.Utilisateur;
import com.s2f.s2fapi.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user =
                Utilisateur.builder()
                        .prenom(registerRequest.getFirstname())
                        .nom(registerRequest.getLastname())
                        .email(registerRequest.getEmail())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .role(Role.USER)
                        .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (Exception e) {
            throw new BadRequestException("Invalid credentials");
        }

        var user =
                repository
                        .findByEmail(authenticationRequest.getEmail())
                        .orElseThrow(
                                () -> new EntityNotFoundException(ErrorsMessages.USER_NOT_FOUND));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public UserConnectDto getConnectedUser() {
        String email = getEmail();
        Utilisateur user =
                repository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new BadRequestException(
                                                ErrorsMessages.CANNOT_GET_CONNECTED_USER));
        return buildUserConnectDto(user);
    }

    private String getEmail() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException("User is not authenticated");
        }

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        if (authentication.getPrincipal() instanceof String email) {
            return email;
        }

        throw new BadRequestException("Unable to retrieve email from authentication");
    }

    public UserConnectDto resetPassword(AuthenticationRequest authenticationRequest) {
        Utilisateur user =
                repository
                        .findByEmail(authenticationRequest.getEmail())
                        .orElseThrow(
                                () -> new EntityNotFoundException(ErrorsMessages.USER_NOT_FOUND));

        user.setPasswordChanged(true);
        user.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
        repository.save(user);
        return buildUserConnectDto(user);
    }

    private UserConnectDto buildUserConnectDto(Utilisateur user) {
        UserConnectDto userConnectDto = new UserConnectDto();
        userConnectDto.toUserConnectDto(user);
        return userConnectDto;
    }
}
