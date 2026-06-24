package com.insa.incidentdashboard.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // ይህ ክላስ business logic እንደሚይዝ ይነግራል
@RequiredArgsConstructor // Lombok: ለ final fields constructor ይፈጥራል
public class UserService {

    private final UserRepository userRepository; // UserRepositoryን ይጠቀማል

    public User create(CreateUserRequest request) {

        User user = User.builder() // User object ይፈጥራል
                .username(request.username())
                .email(request.email())
                .password(request.password()) // TODO: የይለፍ ቃል hash ማድረግ አለብን!
                .role(request.role())
                .build();

        return userRepository.save(user); // Userን ወደ ዳታቤዝ ያስቀምጣል
    }
}