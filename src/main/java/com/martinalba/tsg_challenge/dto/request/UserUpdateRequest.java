package com.martinalba.tsg_challenge.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String password;
}
