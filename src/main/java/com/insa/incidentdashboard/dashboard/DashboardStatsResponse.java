package com.insa.incidentdashboard.dashboard;

// Record class: ከDTOs (Data Transfer Objects) ጋር ለመስራት ጥሩ ነው።
public record DashboardStatsResponse(
        long totalIncidents,
        long openIncidents,
        long inProgressIncidents,
        long resolvedIncidents,
        long criticalIncidents
) {
}