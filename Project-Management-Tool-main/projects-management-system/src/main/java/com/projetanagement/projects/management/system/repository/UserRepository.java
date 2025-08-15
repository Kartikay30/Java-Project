package com.projetanagement.projects.management.system.repository;

import com.projetanagement.projects.management.system.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);  // Returns User directly
}