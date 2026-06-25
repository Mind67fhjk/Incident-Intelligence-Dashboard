package com.insa.incidentdashboard.incident;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByStatus(IncidentStatus Status);
    List<Incident> findBySeverity(Severity severity);
}