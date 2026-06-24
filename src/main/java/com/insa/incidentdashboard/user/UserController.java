package com.insa.incidentdashboard.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // ይህ ክላስ RESTful API endpoints እንደሚይዝ ይነግራል
@RequestMapping("/api/users") // ሁሉም endpoints በዚህ base path ስር ይሆናሉ
@RequiredArgsConstructor // Lombok: ለ final fields constructor ይፈጥራል
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository; // ለ GET request በቀጥታ እንጠቀማለን

    @PostMapping // POST request ለ /api/users
    public User create(@RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @GetMapping // GET request ለ /api/users
    public List<User> getAll() {
        return userRepository.findAll(); // ሁሉንም users ከዳታቤዝ ያመጣል
    }
}