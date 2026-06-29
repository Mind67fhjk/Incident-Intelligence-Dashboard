package com.insa.incidentdashboard.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity // ይህ ክላስ የዳታቤዝ ሰንጠረዥን እንደሚወክል ይነግራል
@Table(name = "users") // የሰንጠረዡ ስም "users" እንዲሆን
@Getter // Lombok: getters ይፈጥራል
@Setter // Lombok: setters ይፈጥራል
@NoArgsConstructor // Lombok: ነባሪ constructor ይፈጥራል
@AllArgsConstructor // Lombok: ሁሉንም fields የሚቀበል constructor ይፈጥራል
@Builder // Lombok: builder pattern ይፈጥራል

public class User implements UserDetails{

    @Id // ይህ primary key መሆኑን ይነግራል
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID በራስ-ሰር እንዲፈጠር
    private Long id;

    @Column(unique = true, nullable = false) // username ልዩ እና ባዶ መሆን የለበትም
    private String username;

    @Column(unique = true, nullable = false) // email ልዩ እና ባዶ መሆን የለበትም
    private String email;

    @Column(nullable = false) // password ባዶ መሆን የለበትም
    private String password;

    @Enumerated(EnumType.STRING) // Role እንደ String በዳታቤዝ ውስጥ እንዲቀመጥ
    @Column(nullable = false)
    private UserRole role; // UserRole የሚባል enum እንፈጥራለን

    // እነዚህን ዘዴዎች በUser ክላስ ውስጥ አስገባ
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ተጠቃሚው ያለውን ሚና (role) ወደ Spring Security የሚረዳው GrantedAuthority ይቀይራል
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        // የይለፍ ቃሉን ከUser entity ይመልሳል
        return password;
    }

    @Override
    public String getUsername() {
        // የተጠቃሚውን ስም ከUser entity ይመልሳል
        return username; // እዚህ ላይ usernameን እንጠቀማለን (emailም መጠቀም ይቻላል)
    }

    @Override
    public boolean isAccountNonExpired() {
        // የተጠቃሚው አካውንት ጊዜው አላለፈበትም (ሁልጊዜ true)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // የተጠቃሚው አካውንት አልተቆለፈም (ሁልጊዜ true)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // የተጠቃሚው የይለፍ ቃል ጊዜው አላለፈበትም (ሁልጊዜ true)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // ተጠቃሚው ነቅቷል (ሁልጊዜ true)
        return true;
    }
}