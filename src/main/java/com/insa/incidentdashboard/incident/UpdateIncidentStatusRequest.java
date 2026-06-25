package com.insa.incidentdashboard.incident;

import jakarta.validation.constraints.NotNull; // ይህን አስገባ

public record UpdateIncidentStatusRequest(
        @NotNull(message = "Status cannot be null") // ሁኔታው ባዶ መሆን እንደሌለበት እንነግረዋለን
        IncidentStatus status
) {
}