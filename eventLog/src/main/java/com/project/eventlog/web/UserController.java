package com.project.eventlog.web;

import com.project.eventlog.domain.dtos.binding.UserChangeUsernameBindingModel;
import com.project.eventlog.domain.dtos.binding.UserEditBindingModel;
import com.project.eventlog.domain.dtos.service.UserChangeUsernameServiceModel;
import com.project.eventlog.domain.dtos.service.UserEditServiceModel;
import com.project.eventlog.domain.dtos.view.EventViewModel;
import com.project.eventlog.domain.dtos.view.UserViewModel;
import com.project.eventlog.domain.enums.LocationEnum;
import com.project.eventlog.service.EventService;
import com.project.eventlog.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.EnumSet;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final EventService eventService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, EventService eventService, ModelMapper modelMapper) {
        this.userService = userService;
        this.eventService = eventService;
        this.modelMapper = modelMapper;
    }

    // ACCESS TO ALL USERS

    @GetMapping("/all-users")
    public String getAllUsers(Model model) {

        List<UserViewModel> userViewModelList = userService.getAllUsers();
        model.addAttribute("allUsers", userViewModelList);
        return "users";
    }

    // ACCESS TO MY PROFILE
    @GetMapping("/my-profile")
    public String getUserProfile(Model model,
                                 @AuthenticationPrincipal UserDetails currentUser) {
        UserViewModel userViewModel = userService.getUserByUsername(currentUser.getUsername());

        model.addAttribute("userViewModel", userViewModel);
        model.addAttribute("canEdit", true);
        List<EventViewModel> eventsHostedList = eventService.getEventsWhereUserIsHost(userViewModel.getId());
        List<EventViewModel> eventJoinedList = eventService.getEventsWhereUserIsJoined(userViewModel.getId());


        model.addAttribute("eventsHostedList", eventsHostedList);
        model.addAttribute("eventsJoinedList", eventJoinedList);
        return "user-profile";
    }

    // ACCESS TO ANY USER PROFILE BY ID

    @GetMapping("/{userId}/profile")
    public String getUserProfile(@PathVariable Long userId, Model model,
                                 Principal principal) {

        UserViewModel userViewModel = userService.getUserById(userId);
        boolean canEdit = canEdit(principal.getName(), userViewModel.getUsername());

        model.addAttribute("userViewModel", userViewModel);
        model.addAttribute("canEdit", canEdit);

        List<EventViewModel> eventsHostedList = eventService.getEventsWhereUserIsHost(userViewModel.getId());
        List<EventViewModel> eventJoinedList = eventService.getEventsWhereUserIsJoined(userViewModel.getId());


        model.addAttribute("eventsHostedList", eventsHostedList);
        model.addAttribute("eventsJoinedList", eventJoinedList);


        return "user-profile";
    }


    // ACCESS TO EDIT USER (FOR ADMIN OR OWNER)
    @GetMapping("/{userId}/edit")
    public String editUserForm(@PathVariable Long userId, Model model, Principal principal) {
        UserViewModel userViewModel = userService.getUserById(userId);
        if (userViewModel == null) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        boolean canEdit = canEdit(principal.getName(), userViewModel.getUsername());


        UserEditBindingModel userEditBindingModel = modelMapper.map(userViewModel, UserEditBindingModel.class);
        model.addAttribute("userViewModel", userViewModel);
        model.addAttribute("userEditBindingModel", userEditBindingModel);
        model.addAttribute("locations", EnumSet.allOf(LocationEnum.class));
        model.addAttribute("canEdit", canEdit);

        return "user-profile-edit";

    }

    // EDIT USER (FOR ADMIN OR OWNER)
    @PostMapping("/{userId}/edit")
    public String editUser(@PathVariable Long userId,
                           @Valid UserEditBindingModel userEditBindingModel, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditBindingModel", bindingResult);
            return "redirect:/users/" + userId + "/edit";
        }


        try {
            UserEditServiceModel userEditServiceModel = modelMapper.map(userEditBindingModel, UserEditServiceModel.class);
            userService.editUser(userId, userEditServiceModel);
            redirectAttributes.addFlashAttribute("successMessage", "User successfully updated.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the user.");
        }

        return "redirect:/users/" + userId + "/profile";
    }


    // ACCESS CHANGE USERNAME AND PASSWORD (FOR ADMIN OR OWNER)
    @GetMapping("/{userId}/change-username")
    public String changeUsernameAndPasswordForm(@PathVariable Long userId, Model model) {
        UserViewModel userViewModel = userService.getUserById(userId);

        UserChangeUsernameBindingModel userChangeUsernameBindingModel = modelMapper.map(userViewModel, UserChangeUsernameBindingModel.class);
        model.addAttribute("userViewModel", userViewModel);
        model.addAttribute("userChangeUsernameBindingModel", userChangeUsernameBindingModel);

        return "user-change-username";

    }

    // CHANGE USERNAME AND PASSWORD (FOR ADMIN OR OWNER)

    @PostMapping("/{userId}/change-username")
    public String changeUsernameAndPassword(@PathVariable Long userId,
                                            @Valid UserChangeUsernameBindingModel userChangeUsernameBindingModel, BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userChangeUsernameBindingModel", userChangeUsernameBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userChangeUsernameBindingModel", bindingResult);
            return "redirect:/users/" + userId + "/change-username";
        }

        try {
            UserChangeUsernameServiceModel userChangeUsernameServiceModel = modelMapper.map(userChangeUsernameBindingModel, UserChangeUsernameServiceModel.class);
            userService.changeUsername(userId, userChangeUsernameServiceModel);
            redirectAttributes.addFlashAttribute("successMessage", "Username successfully updated.");
            request.logout();
        } catch (IllegalArgumentException | ServletException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/users/" + userId + "/profile";
    }


    // HELPERS
    private boolean canEdit(String currentUsername, String username) {
        boolean isAdmin = userService.isUserAdmin(currentUsername);
        boolean isOwner = currentUsername.equals(username);
        return isAdmin || isOwner;

    }
}
