package com.insa.incidentdashboard.incident;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.insa.incidentdashboard.dto.NoteResponse;
import com.insa.incidentdashboard.dto.CreateNoteRequest;
import com.insa.incidentdashboard.incident.IncidentResponse; // ይህ እንዳለህ እርግጠኛ ሁን
import com.insa.incidentdashboard.incident.UpdateIncidentStatusRequest; // ይህ እንዳለህ እርግጠኛ ሁን
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;
    private final IncidentRepository incidentRepository;

    // 1. ማስታወሻ ለመጨመር
    @PostMapping("/{incidentId}/notes")
    public ResponseEntity<NoteResponse> addNoteToIncident(
            @PathVariable Long incidentId,
            @Valid @RequestBody CreateNoteRequest request) {
        NoteResponse response = incidentService.addNoteToIncident(incidentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. የማስታወሻዎች ሊስት ለማግኘት (እዚህ ጋር የነበረው '}' ተወግዷል፣ 'NoteResponse' ስህተቱም ተስተካክሏል)
    @GetMapping("/{incidentId}/notes")
    public ResponseEntity<List<NoteResponse>> getNotesForIncident(@PathVariable Long incidentId) {
        List<NoteResponse> notes = incidentService.getNotesForIncident(incidentId);
        return ResponseEntity.ok(notes);
    }

    // 3. አዲስ ክስተት ለመፍጠር (ለሙከራ)
    @PostMapping
    public ResponseEntity<IncidentResponse> createIncident(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Severity severity) {
        IncidentResponse response = incidentService.createIncident(title, description, severity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 4. ሁሉንም ክስተቶች ለማግኘት
    @GetMapping
    public ResponseEntity<List<IncidentResponse>> getAllIncidents() {
        List<IncidentResponse> incidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }

    // 5. የክስተቱን ሁኔታ የሚያዘምን endpoint
    @PatchMapping("/{id}/status")
    public ResponseEntity<IncidentResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIncidentStatusRequest request) {
        IncidentResponse response = incidentService.updateStatus(id, request);
        return ResponseEntity.ok(response);
    }

    // 6. በሁኔታ (Status) የሚያጣራ API
    @GetMapping("/status/{status}")
    public List<IncidentResponse> getByStatus(@PathVariable IncidentStatus status) {
        return incidentRepository.findByStatus(status)
                .stream()
                .map(incidentService::toResponse)
                .toList();
    }

    // 7. በSeverity የሚያጣራ API
    @GetMapping("/severity/{severity}")
    public List<IncidentResponse> getBySeverity(@PathVariable Severity severity) {
        return incidentRepository.findBySeverity(severity)
                .stream()
                .map(incidentService::toResponse)
                .toList();
    }
} // ክላሱ በትክክል የሚያበቃው እዚህ የመጨረሻው መስመር ላይ ብቻ ነው!