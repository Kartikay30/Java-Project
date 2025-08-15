package com.projetanagement.projects.management.system.service;


import com.projetanagement.projects.management.system.Models.Chat;
import com.projetanagement.projects.management.system.Models.Project;
import com.projetanagement.projects.management.system.Models.User;
import com.projetanagement.projects.management.system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImplementation implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;


    @Autowired
    private ChatService chatService;



    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createProject = new Project();
        createProject.setOwner(user);
        createProject.setTags(project.getTags());
        createProject.setName(project.getName());
        createProject.setCategory(project.getCategory());
        createProject.setDescription(project.getDescription());
        createProject.getTeam().add(user);
        Project savedProject= projectRepository.save(createProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);
        chat.setName("Project Chat: " + savedProject.getName());
        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return projectRepository.save(savedProject);
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects=projectRepository.findByTeamContainingOrOwner(user, user);
        if (category!=null){
            projects=projects.stream().filter(project -> project.getCategory().equals(category)).toList();
        }
        if (tag!=null){
            projects=projects.stream().filter(project -> project.getTags().contains(tag)).toList();
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()){
            throw new Exception("Project not found");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, long userId) throws Exception {
        getChatById(projectId);
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updateProject, Long id) throws Exception {
        Project project = getProjectById(id);
        project.setName(updateProject.getName());
        project.setDescription(updateProject.getDescription());
        project.setTags(updateProject.getTags());
         return  projectRepository.save(project);
    }

    @Override
    public void addToProject(Long ProjectId, User user) throws Exception {
        Project project = getProjectById(ProjectId);
        User currentUser = userService.findUserById(user.getId());
        if (!project.getTeam().contains(user)){
            project.getTeam().remove(user);
            project.getChat().getUsers().add(user) ;
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long ProjectId, User user) throws Exception {
        Project project = getProjectById(ProjectId);
        User currentUser = userService.findUserById(user.getId());
        if (project.getTeam().contains(user)){
            project.getTeam().remove(user);
            project.getChat().getUsers().remove(user) ;
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatById(Long projectId) throws Exception {
        Project project = getProjectById(projectId);

         return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) throws Exception {

       return projectRepository.findByNameAndTeamContains(keyword, user);
    }



}