package com.insa.incidentdashboard.user;

public record CreateUserRequest(
        String username,
        String email,
        String password,
        UserRole role
) {
}