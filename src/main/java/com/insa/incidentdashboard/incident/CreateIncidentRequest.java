package com.insa.incidentdashboard.incident;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateIncidentRequest(
        @NotBlank(message = "Title cannot be blank")
        String title,
        String description,
        @NotNull(message = "Severity cannot be null")
        Severity severity,
        Long assignedUserId,
        @NotNull(message = "Incident date cannot be null")
        String incidentDate // እንደ String ተቀብለን በኋላ ወደ LocalDateTime እንቀይረዋለን
) {
}