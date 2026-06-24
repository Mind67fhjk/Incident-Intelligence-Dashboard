package com.insa.incidentdashboard.incident;

import java.time.LocalDateTime;

public record IncidentResponse(
        Long id,
        String title,
        String description,
        Severity severity,
        IncidentStatus status,
        Long assignedUserId,
        String assignedUsername,
        Long reportedByUserId,
        String reportedByUsername,
        LocalDateTime incidentDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}