package com.s2f.s2fapi.controller.auth;

import com.s2f.s2fapi.dto.request.AuthenticationRequest;
import com.s2f.s2fapi.dto.request.RegisterRequest;
import com.s2f.s2fapi.dto.response.AuthenticationResponse;
import com.s2f.s2fapi.dto.response.UserConnectDto;
import com.s2f.s2fapi.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/v1/auth/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/v1/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/v1/connected-user")
    public ResponseEntity<UserConnectDto> connectedUser() {
        return ResponseEntity.ok(authenticationService.getConnectedUser());
    }

    @PutMapping("/v1/reset-password")
    public ResponseEntity<UserConnectDto> resetPassword(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.resetPassword(authenticationRequest));
    }
}
