package com.projetanagement.projects.management.system.service;

import com.projetanagement.projects.management.system.Models.User;

public interface UserService {
    User findUserProfileByJwt(String jwt)throws Exception;

    User findByEmail(String email)throws Exception;

    User findUserById(Long userId)throws Exception;

    User updateUsersProjectSize(User user, int number);


}
