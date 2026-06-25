package com.insa.incidentdashboard.dashboard;

import com.insa.incidentdashboard.incident.*; // እነዚህን አስገባ
import lombok.RequiredArgsConstructor; // ይህን አስገባ
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // ይህ ክላስ RESTful API ጥያቄዎችን እንደሚቀበል ይነግራል።
@RequiredArgsConstructor // Lombok: ለ`final` fields constructor ይፈጥራል።
public class DashboardController {

    private final IncidentRepository incidentRepository; // IncidentRepositoryን ይጠቀማል

    @GetMapping("/api/dashboard/stats") // ይህ API endpoint ይሆናል
    public DashboardStatsResponse stats() {

        return new DashboardStatsResponse(
                incidentRepository.count(), // ሁሉንም ክስተቶች ይቆጥራል
                incidentRepository.countByStatus(IncidentStatus.OPEN), // Open የሆኑትን ይቆጥራል
                incidentRepository.countByStatus(IncidentStatus.IN_PROGRESS), // In Progress የሆኑትን ይቆጥራል
                incidentRepository.countByStatus(IncidentStatus.RESOLVED), // Resolved የሆኑትን ይቆጥራል
                incidentRepository.countBySeverity(Severity.CRITICAL) // Critical የሆኑትን ይቆጥራል
        );
    }
}