package com.projetanagement.projects.management.system.service;

import com.projetanagement.projects.management.system.Models.Chat;
import com.projetanagement.projects.management.system.Models.Project;
import com.projetanagement.projects.management.system.Models.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, User user)throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag)throws Exception ;

    Project getProjectById(Long projectId)throws Exception;


    void deleteProject(Long projectId, long userId)throws Exception;

    Project updateProject(Project updateProject, Long id)throws  Exception;

    void addToProject(Long ProjectId, User user)throws  Exception;

    void removeUserFromProject(Long ProjectId, User user)throws  Exception;

    Chat getChatById(Long projectId)throws Exception;

    List<Project> searchProject (String keyword, User user)throws Exception;

}
