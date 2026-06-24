package com.insa.incidentdashboard.incident;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;
    private final IncidentRepository incidentRepository; // ለጊዜው getAllን ለመጠቀም

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IncidentResponse create(
            @Valid @RequestBody CreateIncidentRequest request) {
        return incidentService.create(request);
    }

    @GetMapping
    public List<IncidentResponse> getAll() {
        return incidentRepository.findAll()
                .stream()
                .map(incidentService::toResponse)
                .toList();
    }
}