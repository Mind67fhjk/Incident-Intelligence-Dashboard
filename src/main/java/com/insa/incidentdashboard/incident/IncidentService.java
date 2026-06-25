package com.insa.incidentdashboard.incident;

import com.insa.incidentdashboard.user.User;
import com.insa.incidentdashboard.user.UserRepository;
import com.insa.incidentdashboard.dto.CreateNoteRequest;
import com.insa.incidentdashboard.dto.NoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final IncidentNoteRepository incidentNoteRepository;

    public NoteResponse addNoteToIncident(Long incidentId, CreateNoteRequest request){
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incident not found with ID: " + incidentId));

        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with ID: " + request.authorId()));

        // ✅ 1. እዚህ ጋር ስፔሊንጉ ተስተካክሏል (.author)
        IncidentNote incidentNote = IncidentNote.builder()
                .incident(incident)
                .author(author)
                .note(request.note())
                .build();

        IncidentNote savedNote = incidentNoteRepository.save(incidentNote);

        return new NoteResponse(
                savedNote.getId(),
                savedNote.getAuthor().getUsername(),
                savedNote.getNote(),
                savedNote.getCreatedAt()
        );
    }

    public List<NoteResponse> getNotesForIncident(Long incidentId){
        // ✅ 2. ፍተሻው መሆን ያለበት በኢንሲደንት ሪፖዚቶሪ ነው
        if(!incidentRepository.existsById(incidentId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incident not found with ID: " + incidentId);
        }

        // ✅ 3. አይነት እና ሜተድ እዚህ ጋር ተስተካክለዋል (findByIncidentId መኖሩን አረጋግጥ)
        List<IncidentNote> notes = incidentNoteRepository.findByIncidentId(incidentId);

        return notes.stream()
                .map(note -> new NoteResponse(
                        note.getId(),
                        note.getAuthor().getUsername(),
                        note.getNote(),
                        note.getCreatedAt() // ✅ 4. ከ .createdAt() ወደ .getCreatedAt() ተቀይሯል
                ))
                .collect(Collectors.toList());
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

    public IncidentResponse createIncident(String title, String description, Severity severity) {
        User reportedByUser = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Reported by user not found"));

        Incident newIncident = Incident.builder()
                .title(title)
                .description(description)
                .severity(severity)
                .status(IncidentStatus.OPEN)
                .incidentDate(LocalDateTime.now())
                .reportedByUser(reportedByUser)
                .build();

        Incident savedIncident = incidentRepository.save(newIncident);
        return toResponse(savedIncident);
    }

    public List<IncidentResponse> getAllIncidents() {
        return incidentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public IncidentResponse updateStatus(Long incidentId, UpdateIncidentStatusRequest request) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new RuntimeException("Incident not found with ID: " + incidentId));

        incident.setStatus(request.status());
        incident = incidentRepository.save(incident);
        return toResponse(incident);
    }

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