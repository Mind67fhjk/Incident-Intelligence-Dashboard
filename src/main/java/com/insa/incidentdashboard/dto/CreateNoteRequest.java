package com.insa.incidentdashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNoteRequest(
        @NotNull(message = "Author ID cannot be null")
        Long authorId,
        @NotBlank(message = "Note cannot be empty")
        String note
) {
}