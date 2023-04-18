package com.project.eventlog.web;

import com.project.eventlog.domain.dtos.binding.UserRegisterBindingModel;
import com.project.eventlog.domain.dtos.service.UserRegistrationServiceModel;
import com.project.eventlog.domain.enums.LocationEnum;
import com.project.eventlog.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.EnumSet;

@Controller
@RequestMapping("/users")
public class RegistrationController {
    private final UserService userService;
    private final ModelMapper modelMapper;


    public RegistrationController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // ACCESS TO REGISTRATION
    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("locations", EnumSet.allOf(LocationEnum.class));
        return "user-auth-register";
    }

    // REGISTER
    @PostMapping("/register")
    public String postRegister(@Valid UserRegisterBindingModel userRegisterBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";
        }



        UserRegistrationServiceModel serviceModel = modelMapper.map(userRegisterBindingModel, UserRegistrationServiceModel.class);
        userService.registerUser(serviceModel);

        return "redirect:/users/login";
    }


    @ModelAttribute("errorMessage")
    public String errorMessage(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Please correct the errors below.";
        }
        return null;
    }

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel userBindingModel() {
        return new UserRegisterBindingModel();
    }
}

