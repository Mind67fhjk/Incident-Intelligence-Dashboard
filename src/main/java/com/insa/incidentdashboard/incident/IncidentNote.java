package com.insa.incidentdashboard.incident;

import com.insa.incidentdashboard.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // አንድ Incident ብዙ IncidentNote ሊኖረው ይችላል። ይህ IncidentNote ከአንድ Incident ጋር መያያዝ አለበት።
    @JoinColumn(name = "incident_id", nullable = false) // የIncident idን እንደ foreign key ይጠቀማል።
    private Incident incident;

    @ManyToOne(optional = false) // አንድ User ብዙ IncidentNote ሊጽፍ ይችላል። ይህ IncidentNote ከአንድ User ጋር መያያዝ አለበት።
    @JoinColumn(name = "author_id", nullable = false) // የUser idን እንደ foreign key ይጠቀማል።
    private User author;

    @Column(nullable = false, length = 2000) // ማስታወሻው ባዶ መሆን የለበትም፣ ቢበዛ 2000 ፊደላት።
    private String note;

    private LocalDateTime createdAt;

    @PrePersist // ይህ ሜተድ Entityው ወደ ዳታቤዝ ከመቀመጡ በፊት ይሰራል።
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}