package com.martinalba.tsg_challenge.controller;

import com.martinalba.tsg_challenge.dto.request.LoginRequest;
import com.martinalba.tsg_challenge.dto.request.RegisterRequest;
import com.martinalba.tsg_challenge.dto.response.TokenResponse;
import com.martinalba.tsg_challenge.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
        TokenResponse token = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final LoginRequest request) {
        final TokenResponse token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        return authService.refreshToken(authHeader);
    }
}
