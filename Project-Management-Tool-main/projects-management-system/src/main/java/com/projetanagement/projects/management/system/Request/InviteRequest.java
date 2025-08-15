package com.projetanagement.projects.management.system.Request;


import com.projetanagement.projects.management.system.Models.Project;
import com.projetanagement.projects.management.system.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteRequest {
    private Long projectId;
    private String email;

    private ProjectService projectService;
    public Project getProject() throws Exception {
        return projectService.getProjectById(projectId);
    }
}
