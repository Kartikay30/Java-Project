package com.projetanagement.projects.management.system.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String token ;
    private String email ;
    private Long projectId;


    public void setEmail(String email) {
        this.email = email;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getToken() {
       return this.token = token;

    }

    public Long getProjectId() {
        return this.projectId;
    }
}
