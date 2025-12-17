package com.martinalba.tsg_challenge.mapper;

import com.martinalba.tsg_challenge.dto.response.UserResponse;
import com.martinalba.tsg_challenge.entities.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
