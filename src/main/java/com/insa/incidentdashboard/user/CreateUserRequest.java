package com.insa.incidentdashboard.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,
        UserRole role
) {
}