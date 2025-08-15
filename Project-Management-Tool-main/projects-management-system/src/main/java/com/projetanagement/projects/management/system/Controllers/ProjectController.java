package com.projetanagement.projects.management.system.Controllers;


import com.projetanagement.projects.management.system.Models.Chat;
import com.projetanagement.projects.management.system.Models.Invitation;
import com.projetanagement.projects.management.system.Models.Project;
import com.projetanagement.projects.management.system.Models.User;
import com.projetanagement.projects.management.system.Request.InviteRequest;
import com.projetanagement.projects.management.system.Response.MessageResponse;
import com.projetanagement.projects.management.system.service.InvitationService;
import com.projetanagement.projects.management.system.service.ProjectService;
import com.projetanagement.projects.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Project>>getProjects(
            @RequestParam(required = false)String category,
            @RequestParam(required = false)String tag,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        List<Project>projects = projectService.getProjectByTeam(user,category,tag);
        return ResponseEntity.ok(projects);
    }

    @Autowired
    private InvitationService invitationService;

    @GetMapping("/{projectId}")
    public ResponseEntity<Project>getProjectsById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project projects = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

   @PostMapping
    public ResponseEntity<Project>createProject(
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project createProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createProject, HttpStatus.OK);

    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project>updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project updateProject= projectService.updateProject(project, projectId);
        return new ResponseEntity<>(updateProject, HttpStatus.OK);

    }

   @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse>deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
       MessageResponse res = new MessageResponse("Project deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>>searchProjects(
            @RequestParam(required = false)String keyword,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        List<Project>projects = projectService.searchProject(keyword, user);
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat>getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatById(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestBody InviteRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        // Invite handling logic
        Project createProject = projectService.createProject(req.getProject(), user);  // Assuming InviteRequest has a project
        MessageResponse res = new MessageResponse("Project invited successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping("/accept_Invitation")
    public ResponseEntity<Invitation>acceptInvitationProject(
            @RequestBody String token,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addToProject(invitation.getProjectId(), user);
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }
}




