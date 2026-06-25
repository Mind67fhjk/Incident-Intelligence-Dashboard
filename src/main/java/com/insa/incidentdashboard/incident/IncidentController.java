package com.insa.incidentdashboard.incident;

import lombok.RequiredArgsConstructor; // ይህን አስገባ
import org.springframework.http.HttpStatus; // ይህን አስገባ
import org.springframework.http.ResponseEntity; // ይህን አስገባ
import org.springframework.web.bind.annotation.*; // እነዚህን አስገባ


import jakarta.validation.Valid; // ይህን አስገባ

import java.util.List;

@RestController // ይህ ክላስ RESTful API ጥያቄዎችን እንደሚይዝ ይነግረናል
@RequestMapping("/api/incidents") // ሁሉም የዚህ Controller endpoints በ "/api/incidents" ይጀምራሉ
@RequiredArgsConstructor // Lombok: IncidentServiceን በ constructor injection ለማስገባት
public class IncidentController {

    private final IncidentService incidentService; // IncidentServiceን ወደ Controller ውስጥ እናስገባለን
    private final IncidentRepository incidentRepository;
    // አዲስ ክስተት ለመፍጠር (ለሙከራ)
    @PostMapping
    public ResponseEntity<IncidentResponse> createIncident(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Severity severity) {
        IncidentResponse response = incidentService.createIncident(title, description, severity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ሁሉንም ክስተቶች ለማግኘት
    @GetMapping
    public ResponseEntity<List<IncidentResponse>> getAllIncidents() {
        List<IncidentResponse> incidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }

    // የክስተቱን ሁኔታ የሚያዘምን endpoint
    @PatchMapping("/{id}/status") // PATCH ጥያቄዎችን ይቀበላል። "{id}" የክስተቱን ID ከURL ይቀበላል።
    public ResponseEntity<IncidentResponse> updateStatus(
            @PathVariable Long id, // ከURL የሚመጣውን ID ይቀበላል
            @Valid @RequestBody UpdateIncidentStatusRequest request) { // ከጥያቄው አካል (body) የሚመጣውን request ይቀበላል
        IncidentResponse response = incidentService.updateStatus(id, request);
        return ResponseEntity.ok(response); // የተዘመነውን ክስተት ይመልሳል
    }

    // በሁኔታ (Status) የሚያጣራ API
    @GetMapping("/status/{status}")
    public List<IncidentResponse> getByStatus(
            @PathVariable IncidentStatus status) { // Path Variableን ይቀበላል

        return incidentRepository.findByStatus(status)
                .stream()
                .map(incidentService::toResponse)
                .toList();
    }

    // በSeverity የሚያጣራ API
    @GetMapping("/severity/{severity}")
    public List<IncidentResponse> getBySeverity(
            @PathVariable Severity severity) { // Path Variableን ይቀበላል

        return incidentRepository.findBySeverity(severity)
                .stream()
                .map(incidentService::toResponse)
                .toList();
    }

}