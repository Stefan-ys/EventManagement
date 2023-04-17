package com.project.eventlog.web;

import com.project.eventlog.domain.dtos.view.UserViewModel;
import com.project.eventlog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserManagementController {
    private final UserService userService;


    public UserManagementController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/management")
    public String getManagementPage(Model model) {
        List<UserViewModel> userViewModelList = userService.getAllUsers();
        model.addAttribute("allUsers", userViewModelList);
        return "management";
    }


    @PostMapping("/users/{userId}/change-role")
    public String changeUserRole(@PathVariable Long userId) {
        userService.changeRole(userId);
        return "redirect:/users/management";
    }

}
