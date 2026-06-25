package com.insa.incidentdashboard.incident;

import com.insa.incidentdashboard.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructorን ይተካል
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT") // ለረጅም ጽሁፍ
    private String description;

    @Enumerated(EnumType.STRING) // Enum እሴቶችን በዳታቤዝ ውስጥ እንደ String ለማስቀመጥ
    @Column(nullable = false) // ሁኔታው ባዶ መሆን የለበትም
    private IncidentStatus status; // የክስተቱ ሁኔታ (OPEN, IN_PROGRESS, RESOLVED, CLOSED)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity; // የክስተቱ ክብደት (LOW, MEDIUM, HIGH, CRITICAL)

    @ManyToOne(fetch = FetchType.LAZY) // ብዙ Incident ለአንድ User ሊሆን ይችላል
    @JoinColumn(name = "assigned_user_id") // የውጪ ቁልፍ (Foreign Key)
    private User assignedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_user_id", nullable = false)
    private User reportedByUser; // ማን ክስተቱን እንደዘገበ

    @Column(nullable = false)
    private LocalDateTime incidentDate; // ክስተቱ የተከሰተበት ቀንና ሰዓት

    @Column(nullable = false, updatable = false) // አንዴ ከተፈጠረ በኋላ አይቀየርም
    private LocalDateTime createdAt; // ክስተቱ የተፈጠረበት ቀንና ሰዓት
    private LocalDateTime updatedAt; // ክስተቱ ለመጨረሻ ጊዜ የተሻሻለበት ቀንና ሰዓት

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = IncidentStatus.OPEN; // ነባሪ ሁኔታ
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}