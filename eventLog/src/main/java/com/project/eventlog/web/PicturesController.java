package com.project.eventlog.web;

import com.project.eventlog.domain.dtos.binding.PictureBindingModel;
import com.project.eventlog.domain.dtos.service.PictureServiceModel;
import com.project.eventlog.domain.dtos.view.PictureViewModel;
import com.project.eventlog.service.PictureService;
import com.project.eventlog.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/pictures")
public class PicturesController {

    private final UserService userService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public PicturesController(UserService userService, PictureService pictureService, ModelMapper modelMapper) {
        this.userService = userService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{eventId}/add-picture")
    public String showAddPicturesForm(@PathVariable Long eventId, Model model) {
        PictureBindingModel pictureBindingModel = new PictureBindingModel();
        List<PictureViewModel> pictureViewModels = pictureService.getPicturesFromEvent(eventId);
        model.addAttribute("pictureBindingModel", pictureBindingModel);
        model.addAttribute("pictureViewModels", pictureViewModels);
        model.addAttribute("eventId", eventId);
        return "event-add-pictures";
    }

    @PostMapping("/{eventId}/add-picture")
    public String addPicturesToEvent(@PathVariable Long eventId,
                                     @Valid @ModelAttribute("pictureBindingModel") PictureBindingModel pictureBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("pictureBindingModel", pictureBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.pictureBindingModel", bindingResult);

            return "event-add-pictures";
        }

        PictureServiceModel pictureServiceModel = modelMapper.map(pictureBindingModel, PictureServiceModel.class);
        pictureServiceModel.setEventId(eventId);
        pictureServiceModel.setAuthorId(userService.fetUserIdByUsername(principal.getName()));
        pictureService.addPictureToEvent(pictureServiceModel);


        return "redirect:/pictures/{eventId}/add-picture";
    }

    @PostMapping("/{pictureId}/delete-picture")
    public String deletePicture(@PathVariable Long pictureId, @RequestParam Long eventId) {
        pictureService.deletePicture(pictureId);
        return "redirect:/pictures/" + eventId + "/add-picture";
    }


}
