package com.insa.incidentdashboard.user; // ፓኬጁን አስተካክል

import com.insa.incidentdashboard.user.User;
import com.insa.incidentdashboard.user.UserRepository; // ይህንን ማከል አለብህ
import com.insa.incidentdashboard.user.UserResponse;
import com.insa.incidentdashboard.user.UserService; // ይህንን ማከል አለብህ
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // የመጨረሻው ተልዕኮ ላይ ይህንን እንጨምራለን
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository; // ይህንን ማከል አለብህ

    // Constructor Injection
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping // POST request ለ /api/users
    public User create(@RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userService::toResponse)
                .toList();
    }
}