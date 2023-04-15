package com.project.eventlog.web;

import com.project.eventlog.domain.dtos.binding.EventBindingModel;
import com.project.eventlog.domain.dtos.binding.PictureBindingModel;
import com.project.eventlog.domain.dtos.service.EventServiceModel;
import com.project.eventlog.domain.dtos.view.EventViewModel;
import com.project.eventlog.domain.dtos.view.PictureViewModel;
import com.project.eventlog.domain.dtos.view.UserViewModel;
import com.project.eventlog.domain.enums.CategoryEnum;
import com.project.eventlog.domain.enums.LocationEnum;
import com.project.eventlog.service.EventService;
import com.project.eventlog.service.PictureService;
import com.project.eventlog.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final UserService userService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public EventController(EventService eventService, UserService userService, PictureService pictureService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.userService = userService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    //Get event(s)
    @GetMapping("/{eventId}/details")
    public String getEventById(@PathVariable Long eventId, Model model, Principal principal) {
        EventViewModel eventViewModel = eventService.getEventById(eventId);
        model.addAttribute("event", eventViewModel);
        List<UserViewModel> eventParticipants = eventService.getParticipantsFromEvent(eventId);
        model.addAttribute("participants", eventParticipants);
        model.addAttribute("isEventParticipant", eventService.isEventParticipant(eventId, principal.getName()));
        return "event-details";
    }

    @GetMapping()
    public String getAllEvents(Model model, @RequestParam(defaultValue = "name") String sortBy,
                               @RequestParam(defaultValue = "ALL") String statusFilter) {
        List<EventViewModel> events = eventService.getAllEvents(sortBy, statusFilter);
        model.addAttribute("events", events);
        return "events-all";
    }

    @ModelAttribute("eventBindingModel")
    public EventBindingModel eventBindingModel() {
        return new EventBindingModel();
    }

    //Create event
    @GetMapping("/create")
    public String createEventForm(Model model) {
        model.addAttribute("locations", LocationEnum.values());
        model.addAttribute("categories", CategoryEnum.values());
        return "event-create";
    }

    @PostMapping("/create")
    public String addEvent(@Valid EventBindingModel eventBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal User user) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("eventBindingModel", eventBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.eventBindingModel", bindingResult);

            return "redirect:/events/create";
        }

        EventServiceModel eventServiceModel = modelMapper.map(eventBindingModel, EventServiceModel.class);

        long eventId = eventService.addEvent(eventServiceModel, user.getUsername());


        return "redirect:/events/" + eventId + "/details";
    }
    //Edit event  TO DO

    //Join event

    @PostMapping("/join")
    public String joinEvent(@RequestParam Long eventId,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {
        eventService.joinEvent(eventId, principal.getName());

        return "redirect:/events/" + eventId + "/details";
    }

    //Leave event

    @PostMapping("/leave")
    public String leaveEvent(@RequestParam Long eventId, Principal principal, RedirectAttributes redirectAttributes) {
        eventService.leaveEvent(eventId, principal.getName());
        redirectAttributes.addFlashAttribute("successMessage", "You have successfully left the event!");
        return "redirect:/events/" + eventId + "/details";
    }

    @GetMapping("/{eventId}/add-picture")
    public String showAddPicturesForm(@PathVariable Long eventId, Model model) {
        PictureBindingModel pictureBindingModel = new PictureBindingModel();
        List<PictureViewModel> pictureViewModels = eventService.getPicturesFromEvent(eventId);
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

        PictureViewModel pictureServiceModel = modelMapper.map(pictureBindingModel, PictureViewModel.class);
        pictureServiceModel.setEventId(eventId);
        pictureServiceModel.setAuthorId(userService.fetUserIdByUsername(principal.getName()));
        eventService.addPictureToEvent(pictureServiceModel);


        return "redirect:/events/{eventId}/add-picture";
    }
}
