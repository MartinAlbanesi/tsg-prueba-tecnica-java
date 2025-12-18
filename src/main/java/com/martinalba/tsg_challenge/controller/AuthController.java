package com.martinalba.tsg_challenge.controller;

import com.martinalba.tsg_challenge.dto.request.LoginRequest;
import com.martinalba.tsg_challenge.dto.request.RegisterRequest;
import com.martinalba.tsg_challenge.dto.response.TokenResponse;
import com.martinalba.tsg_challenge.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/refresh")
//    public TokenResponse refresh(HttpServletRequest request) {
//
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        System.out.println("AUTH HEADER = " + authHeader);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new BadCredentialsException("Invalid Authorization header");
//        }
//
//        return authService.refreshToken(authHeader.substring(7));
//    }
}
