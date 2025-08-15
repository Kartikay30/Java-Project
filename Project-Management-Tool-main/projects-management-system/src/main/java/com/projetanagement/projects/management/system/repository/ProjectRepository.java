package com.projetanagement.projects.management.system.repository;

import com.projetanagement.projects.management.system.Models.Project;
import com.projetanagement.projects.management.system.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
//    List<Project>findByOwner(User user);

    List<Project>  findByNameAndTeamContains(String name, User user);

//    @Query("SELECT p FROM Project p join p.team t where t=:user")
//    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
