package com.insa.incidentdashboard.dto;

import java.time.LocalDateTime;

public record NoteResponse(
        Long id,
        String authorUsername, // የጸሃፊውን username እናሳያለን
        String note,
        LocalDateTime createdAt
) {
}