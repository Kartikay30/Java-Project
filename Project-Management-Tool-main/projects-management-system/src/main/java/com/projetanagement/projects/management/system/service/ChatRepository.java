package com.projetanagement.projects.management.system.service;

import com.projetanagement.projects.management.system.Models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
