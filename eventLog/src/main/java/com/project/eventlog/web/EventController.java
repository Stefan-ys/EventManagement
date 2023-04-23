package com.project.eventlog.web;

import com.project.eventlog.domain.dtos.binding.EventBindingModel;
import com.project.eventlog.domain.dtos.binding.UserChangeUsernameBindingModel;
import com.project.eventlog.domain.dtos.service.EventServiceModel;
import com.project.eventlog.domain.dtos.view.EventViewModel;
import com.project.eventlog.domain.dtos.view.UserViewModel;
import com.project.eventlog.domain.enums.CategoryEnum;
import com.project.eventlog.domain.enums.LocationEnum;
import com.project.eventlog.service.EventService;
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
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    private final ModelMapper modelMapper;

    public EventController(EventService eventService, UserService userService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // ACCESS EVENT BY ID
    @GetMapping("/{eventId}/details")
    public String getEventById(@PathVariable Long eventId, Model model, Principal principal) {
        EventViewModel eventViewModel = eventService.getEventById(eventId);

        List<UserViewModel> eventParticipants = eventService.getParticipantsFromEvent(eventId);

        model.addAttribute("event", eventViewModel);
        model.addAttribute("participants", eventParticipants);
        model.addAttribute("isEventParticipant", eventService.isEventParticipant(eventId, principal.getName()));
        model.addAttribute("canEdit", canEdit(principal.getName(), eventViewModel.getHostUsername()));

        return "event-details";
    }

    // ACCESS ALL EVENT

    @GetMapping()
    public String getAllEvents(Model model, @RequestParam(defaultValue = "name") String sortBy,
                               @RequestParam(defaultValue = "ALL") String statusFilter) {
        List<EventViewModel> events = eventService.getAllEvents(sortBy, statusFilter);
        model.addAttribute("events", events);
        return "events-all";
    }

    // CREATE EVENT

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
                           Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("eventBindingModel", eventBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.eventBindingModel", bindingResult);

            return "redirect:/events/create";
        }

        EventServiceModel eventServiceModel = modelMapper.map(eventBindingModel, EventServiceModel.class);


        EventViewModel eventViewModel = eventService.addEvent(eventServiceModel, principal.getName());
        long eventId = eventViewModel.getId();


        return "redirect:/events/" + eventId + "/details";
    }


    // JOIN EVENT
    @PostMapping("/join")
    public String joinEvent(@RequestParam Long eventId,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {
        eventService.joinEvent(eventId, principal.getName());

        return "redirect:/events/" + eventId + "/details";
    }


    // LEAVE EVENT
    @PostMapping("/leave")
    public String leaveEvent(@RequestParam Long eventId, Principal principal) {
        eventService.leaveEvent(eventId, principal.getName());
        return "redirect:/events/" + eventId + "/details";
    }

    // CANCEL EVENT
    @PostMapping("/cancel")
    public String cancelEvent(@RequestParam Long eventId) {
        eventService.cancelEvent(eventId);
        return "redirect:/events/" + eventId + "/details";

    }

    @ModelAttribute("errorMessage")
    public String errorMessage(@Valid UserChangeUsernameBindingModel userChangeUsernameBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Please correct the errors below.";
        }
        return null;
    }


    @ModelAttribute("eventBindingModel")
    public EventBindingModel eventBindingModel() {
        return new EventBindingModel();
    }


    private boolean canEdit(String currentUsername, String username) {
        boolean isAdmin = userService.isUserAdmin(currentUsername);
        boolean isOwner = currentUsername.equals(username);
        return isAdmin || isOwner;

    }
}
