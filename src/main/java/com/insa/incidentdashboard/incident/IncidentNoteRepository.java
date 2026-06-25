package com.insa.incidentdashboard.incident;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentNoteRepository extends JpaRepository<IncidentNote, Long> {
    // አንድ Incident id ሲሰጥ፣ ለዛ Incident የተጻፉ ማስታወሻዎችን የሚመልስ ሜተድ።
    List<IncidentNote> findByIncidentId(Long incidentId);
}