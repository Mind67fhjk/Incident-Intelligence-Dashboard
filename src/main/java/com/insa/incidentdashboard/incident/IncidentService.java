package com.insa.incidentdashboard.incident;

import com.insa.incidentdashboard.user.User;
import com.insa.incidentdashboard.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository; // User Repository እንፈልጋለን

    public IncidentResponse create(CreateIncidentRequest request) {
        // ለጊዜው, ክስተቱን የሚዘግበው User (reportedByUser) ID 1 እንደሆነ እናስብ
        // በኋላ ላይ Spring Security ስንጠቀም ይህንን እናስተካክላለን
        User reportedByUser = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Reported by user not found"));

        User assignedUser = null;
        if (request.assignedUserId() != null) {
            assignedUser = userRepository.findById(request.assignedUserId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }

        // Incident Date Stringን ወደ LocalDateTime መቀየር
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime incidentDateTime = LocalDateTime.parse(request.incidentDate(), formatter);

        Incident incident = Incident.builder()
                .title(request.title())
                .description(request.description())
                .severity(request.severity())
                .status(IncidentStatus.OPEN) // አዲስ ክስተት ሁልጊዜ OPEN ነው
                .assignedUser(assignedUser)
                .reportedByUser(reportedByUser)
                .incidentDate(incidentDateTime)
                .build();

        incident = incidentRepository.save(incident);

        return toResponse(incident);
    }

    public IncidentResponse toResponse(Incident incident) {
        String assignedUsername = null;
        Long assignedUserId = null;
        if (incident.getAssignedUser() != null) {
            assignedUserId = incident.getAssignedUser().getId();
            assignedUsername = incident.getAssignedUser().getUsername();
        }

        String reportedByUsername = null;
        Long reportedByUserId = null;
        if (incident.getReportedByUser() != null) {
            reportedByUserId = incident.getReportedByUser().getId();
            reportedByUsername = incident.getReportedByUser().getUsername();
        }

        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getSeverity(),
                incident.getStatus(),
                assignedUserId,
                assignedUsername,
                reportedByUserId,
                reportedByUsername,
                incident.getIncidentDate(),
                incident.getCreatedAt(),
                incident.getUpdatedAt()
        );
    }
}