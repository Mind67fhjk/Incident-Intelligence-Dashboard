package com.insa.incidentdashboard.user;

import jakarta.persistence.*;
import lombok.*;

@Entity // ይህ ክላስ የዳታቤዝ ሰንጠረዥን እንደሚወክል ይነግራል
@Table(name = "users") // የሰንጠረዡ ስም "users" እንዲሆን
@Getter // Lombok: getters ይፈጥራል
@Setter // Lombok: setters ይፈጥራል
@NoArgsConstructor // Lombok: ነባሪ constructor ይፈጥራል
@AllArgsConstructor // Lombok: ሁሉንም fields የሚቀበል constructor ይፈጥራል
@Builder // Lombok: builder pattern ይፈጥራል
public class User {

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

    // TODO: createdAt, updatedAt fields መጨመር ይቻላል
}