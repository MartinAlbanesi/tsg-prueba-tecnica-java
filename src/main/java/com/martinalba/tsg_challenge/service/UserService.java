package com.martinalba.tsg_challenge.service;

import com.martinalba.tsg_challenge.dto.request.UserUpdateRequest;
import com.martinalba.tsg_challenge.dto.response.UserResponse;
import com.martinalba.tsg_challenge.entities.User;
import com.martinalba.tsg_challenge.mapper.UserMapper;
import com.martinalba.tsg_challenge.repositories.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return UserMapper.toResponse(user);
    }

    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);
        return UserMapper.toResponse(user);
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}