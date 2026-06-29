package com.insa.incidentdashboard.auth;

import com.insa.incidentdashboard.user.UserRepository; // ይህንን አስገባ
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // ይህ ክላስ Service Layer አካል መሆኑን ይነግራል
@RequiredArgsConstructor // Lombok: ለ final fields constructor ይፈጥራል
public class CustomUserDetailsService implements UserDetailsService { // UserDetailsServiceን ይተገብራል

    private final UserRepository userRepository; // UserRepositoryን ይጠቀማል

    @Override // ይህ ዘዴ ከUserDetailsService የመጣ ነው
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException { // ተጠቃሚው ካልተገኘ ይህ ስህተት ይወረወራል

        return userRepository.findByUsername(username) // ተጠቃሚውን በusername ይፈልጋል
                .orElseThrow(() -> // ካልተገኘ
                        new UsernameNotFoundException("User not found: " + username)); // ስህተት ይወረወራል
    }
}