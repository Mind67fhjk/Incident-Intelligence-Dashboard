package com.insa.incidentdashboard.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // ይህ interface የዳታቤዝ ኦፕሬሽኖችን እንደሚያከናውን ይነግራል
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}