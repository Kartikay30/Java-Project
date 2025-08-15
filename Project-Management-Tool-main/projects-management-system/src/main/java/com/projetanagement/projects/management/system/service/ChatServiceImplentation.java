package com.projetanagement.projects.management.system.service;

import com.projetanagement.projects.management.system.Models.Chat;
import org.springframework.stereotype.Service;


@Service
public class ChatServiceImplentation implements ChatService{

    private ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
       return chatRepository.save(chat);
    }
}
