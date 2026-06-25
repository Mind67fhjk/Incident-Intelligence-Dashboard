package com.insa.incidentdashboard.incident;

import com.insa.incidentdashboard.user.User; // User class አስገባ
import com.insa.incidentdashboard.user.UserRepository; // UserRepository አስገባ
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // ይህን አስገባ
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository; // User Repository እንፈልጋለን

    // የIncident Entityን ወደ IncidentResponse DTO የሚቀይር helper method
    public IncidentResponse toResponse(Incident incident) {
        // assignedUser እና reportedByUser መረጃዎችን እዚህ ጋር ማግኘት አለብን
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
                incident.getSeverity(), // ትክክለኛው ቅደም ተከተል: Severity መጀመሪያ
                incident.getStatus(),   // ከዚያ IncidentStatus
                assignedUserId,         // Assigned User ID ጨምር
                assignedUsername,       // Assigned User Username ጨምር
                reportedByUserId,       // Reported By User ID ጨምር
                reportedByUsername,     // Reported By User Username ጨምር
                incident.getIncidentDate(),
                incident.getCreatedAt(),
                incident.getUpdatedAt()
        );
    }

    // አሁን ለሙከራ የሚሆን ቀላል የክስተት መፍጠሪያ ሜቶድ እንጨምር
    // ይህ ሜትድ CreateIncidentRequest የሚባል DTO ስለማይጠቀም, ለሙከራ ብቻ ይጠቅማል
    public IncidentResponse createIncident(String title, String description, Severity severity) {
        // ለጊዜው, ክስተቱን የሚዘግበው User (reportedByUser) ID 1 እንደሆነ እናስብ
        User reportedByUser = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Reported by user not found"));

        Incident newIncident = Incident.builder() // @Builderን መጠቀም ይመረጣል
                .title(title)
                .description(description)
                .severity(severity)
                .status(IncidentStatus.OPEN) // አዲስ ክስተት ሁልጊዜ OPEN ነው
                .incidentDate(LocalDateTime.now())
                .reportedByUser(reportedByUser) // reportedByUserን አስገባ
                // assignedUser እዚህ ጋር null ነው ምክንያቱም በcreateIncident ሜትድ ውስጥ አልተሰጠም
                .build();

        // @PrePersist እና @PreUpdate lifecycle callbacks createdAt እና updatedAtን በራስ-ሰር ይሞላሉ
        // ስለዚህ እዚህ ጋር በእጅ መሙላት አያስፈልግም
        Incident savedIncident = incidentRepository.save(newIncident);
        return toResponse(savedIncident);
    }

    // ሁሉንም ክስተቶች የሚመልስ ሜቶድ
    public List<IncidentResponse> getAllIncidents() {
        return incidentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // የክስተቱን ሁኔታ የሚያዘምን ሜቶድ
    public IncidentResponse updateStatus(
            Long incidentId,
            UpdateIncidentStatusRequest request) {

        // በID ክስተቱን ከዳታቤዝ እናገኛለን። ከሌለ ስህተት እንወረውራለን።
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new RuntimeException("Incident not found with ID: " + incidentId));

        // የክስተቱን ሁኔታ እናዘምነዋለን
        incident.setStatus(request.status());
        // @PreUpdate lifecycle callback updatedAtን በራስ-ሰር ይሞላል
        // incident.setUpdatedAt(LocalDateTime.now()); // እዚህ ጋር በእጅ መሙላት አያስፈልግም

        // የተዘመነውን ክስተት ወደ ዳታቤዝ እናስቀምጣለን
        incident = incidentRepository.save(incident);

        // የተዘመነውን ክስተት ወደ IncidentResponse DTO ቀይረን እንመልሳለን
        return toResponse(incident);
    }

    // ከዚህ በፊት የነበረውን create(CreateIncidentRequest request) ሜትድህን መልሰህ መጠቀም ትችላለህ
    public IncidentResponse create(CreateIncidentRequest request) {
        User reportedByUser = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Reported by user not found"));

        User assignedUser = null;
        if (request.assignedUserId() != null) {
            assignedUser = userRepository.findById(request.assignedUserId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime incidentDateTime = LocalDateTime.parse(request.incidentDate(), formatter);

        Incident incident = Incident.builder()
                .title(request.title())
                .description(request.description())
                .severity(request.severity())
                .status(IncidentStatus.OPEN)
                .assignedUser(assignedUser)
                .reportedByUser(reportedByUser)
                .incidentDate(incidentDateTime)
                .build();

        incident = incidentRepository.save(incident);

        return toResponse(incident);
    }
}