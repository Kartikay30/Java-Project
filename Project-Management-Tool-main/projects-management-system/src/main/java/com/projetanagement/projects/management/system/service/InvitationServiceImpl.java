package com.projetanagement.projects.management.system.service;

import com.projetanagement.projects.management.system.Models.Invitation;
import com.projetanagement.projects.management.system.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class InvitationServiceImpl implements InvitationService{


    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.getToken();
        invitationRepository.save(invitation);

        String invitationLink = "http://localhost:5173/accept_invitation/?token"+ invitationToken;
        emailService.sendEmailWithToken(email, invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation!=null){
           throw new Exception("Invitation not found");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserEmail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteInvitation(String token) {
       Invitation invitation = invitationRepository.findByToken(token);
       invitationRepository.delete(invitation);
    }
}
