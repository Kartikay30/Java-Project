package com.projetanagement.projects.management.system.service;

import com.projetanagement.projects.management.system.Models.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitation(String email, Long projectId) throws MessagingException;

    public Invitation acceptInvitation(String token, Long userId) throws Exception;

    public String getTokenByUserEmail(String userEmail);

    void deleteInvitation(String token);

}
