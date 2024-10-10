package com.s2f.s2fapi.fixtures;

import com.s2f.s2fapi.dto.request.RegisterRequest;
import com.s2f.s2fapi.model.Role;
import com.s2f.s2fapi.model.Utilisateur;
import com.s2f.s2fapi.repository.UtilisateurRepository;
import com.s2f.s2fapi.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log
public class UtilisateurFixture {
    private final AuthenticationService authenticationService;
    private final UtilisateurRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addDefaultAdmin() {
        List<Utilisateur> users = userRepository.findAllByArchiveFalse();
        if (users == null || users.isEmpty()) {

            RegisterRequest registerRequest = new RegisterRequest("admin", "admin", "admin@gmail.com", "admin@123");
            System.out.println(passwordEncoder.encode(registerRequest.getPassword()));
            var user = Utilisateur.builder()
                    .prenom(registerRequest.getFirstname())
                    .nom(registerRequest.getLastname())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(user);
            log.info("default admin added successfully");
        }
    }
}
