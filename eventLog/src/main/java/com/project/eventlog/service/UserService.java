package com.project.eventlog.service;

import com.project.eventlog.domain.dtos.binding.UserChangeUsernameBindingModel;
import com.project.eventlog.domain.dtos.service.UserChangeUsernameServiceModel;
import com.project.eventlog.domain.dtos.service.UserEditServiceModel;
import com.project.eventlog.domain.dtos.service.UserRegistrationServiceModel;
import com.project.eventlog.domain.dtos.view.EventViewModel;
import com.project.eventlog.domain.dtos.view.UserViewModel;

import java.util.List;

public interface UserService {
    boolean checkUserName(String userName);

    boolean checkEmailAddress(String userEmail);

    void registerUser(UserRegistrationServiceModel serviceModel);

    List<UserViewModel> getAllUsers();

    void initializeUsers();

    UserViewModel getUserById(Long id);


    UserViewModel getUserByUsername(String username);

    boolean isUserAdmin(String currentUsername);

    void editUser(Long userId, UserEditServiceModel userEditServiceModel);


    long fetUserIdByUsername(String name);

    void changeUsername(Long userId, UserChangeUsernameServiceModel userChangeUsernameServiceModel);
}
