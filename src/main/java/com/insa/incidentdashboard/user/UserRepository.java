package com.insa.incidentdashboard.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // ይህ interface የዳታቤዝ ኦፕሬሽኖችን እንደሚያከናውን ይነግራል
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository ሁሉንም መሰረታዊ CRUD ኦፕሬሽኖች በነባሪ ይሰጠናል
    // (Create, Read, Update, Delete)
}