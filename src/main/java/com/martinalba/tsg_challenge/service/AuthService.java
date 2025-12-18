// java
package com.martinalba.tsg_challenge.service;

import com.martinalba.tsg_challenge.dto.request.LoginRequest;
import com.martinalba.tsg_challenge.dto.request.RegisterRequest;
import com.martinalba.tsg_challenge.dto.response.TokenResponse;
import com.martinalba.tsg_challenge.entities.User;
import com.martinalba.tsg_challenge.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public TokenResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        var user = userRepository.save(
                User.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .build()
        );

        return issueTokens(user);
    }

    public TokenResponse login(LoginRequest request) {

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return issueTokens(user);
    }

    private TokenResponse issueTokens(User user) {

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new TokenResponse(accessToken, refreshToken);
    }


    public TokenResponse refreshToken(String refreshToken) {

        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
