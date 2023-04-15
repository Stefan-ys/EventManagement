package com.project.eventlog.web;

import com.project.eventlog.domain.dtos.binding.CommentBindingModel;
import com.project.eventlog.domain.dtos.service.CommentServiceModel;
import com.project.eventlog.domain.dtos.view.CommentViewModel;
import com.project.eventlog.service.CommentService;
import com.project.eventlog.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/comments")

public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, UserService userService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping({"/chat-room", "/{eventId}/chat-room"})
    public String getComments(@PathVariable(required = false) Long eventId, Model model) {
        List<CommentViewModel> commentViewModels;
        boolean isEvent;

        if (eventId != null) {
            commentViewModels = commentService.getAllCommentsByEvent(eventId);
            isEvent = true;
            model.addAttribute("eventId", eventId);
        } else {
            commentViewModels = commentService.getAllCommentsWithoutEvent();
            isEvent = false;
        }

        model.addAttribute("commentViewModel", commentViewModels);
        model.addAttribute("commentBindingModel", new CommentBindingModel());
        model.addAttribute("isEvent", isEvent);

        return "/chat-room";
    }

    @PostMapping(value = {"/add-comment", "/{eventId}/add-comment"})
    public String addComment(@PathVariable(required = false) Long eventId,
                             @Valid @ModelAttribute("commentBindingModel") CommentBindingModel commentBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentBindingModel", commentBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentBindingModel", bindingResult);

            return getRedirectPath(eventId);
        }

        CommentServiceModel commentServiceModel = modelMapper.map(commentBindingModel, CommentServiceModel.class)
                .setAuthorId(userService.fetUserIdByUsername(principal.getName()))
                .setDateTime(LocalDateTime.now());
        if (eventId != null) {
            commentServiceModel.setEventId(eventId);
            commentService.addComment(commentServiceModel);
            return "redirect:/comments/" + eventId + "/chat-room";
        } else {
            commentService.addComment(commentServiceModel);
            return "redirect:/comments/chat-room";
        }
    }

    @PostMapping("/{commentId}/add-reply")
    public String addReply(@PathVariable Long commentId,
                           @Valid @ModelAttribute("commentBindingModel") CommentBindingModel commentBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes,
                           @RequestParam(required = false) Long eventId) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentBindingModel", commentBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentBindingModel", bindingResult);

            return getRedirectPath(eventId);
        }

        CommentServiceModel commentServiceModel = modelMapper.map(commentBindingModel, CommentServiceModel.class);
        commentService.addReply(commentId, commentServiceModel);

        return getRedirectPath(eventId);
    }

    @PostMapping("/{commentId}/edit-comment")
    public String editComment(@PathVariable Long commentId, @RequestParam(required = false) Long eventId, @Valid @ModelAttribute("commentBindingModel") CommentBindingModel commentBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentBindingModel", commentBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentBindingModel", bindingResult);

            return getRedirectPath(eventId);
        }

        CommentServiceModel commentServiceModel = modelMapper.map(commentBindingModel, CommentServiceModel.class);
        commentService.editComment(commentId, commentServiceModel);

        return getRedirectPath(eventId);
    }

    @PostMapping("/{commentId}/delete-comment")
    public String deleteComment(@PathVariable Long commentId, @RequestParam(name = "eventId", required = false) Long eventId) {
        commentService.deleteComment(commentId);
        return getRedirectPath(eventId);
    }


    @ModelAttribute("commentBindingModel")
    public CommentBindingModel commentBindingModel() {
        return new CommentBindingModel();
    }

    private String getRedirectPath(Long eventId) {
        if (eventId == null) {
            return "redirect:/comments/chat-room";
        } else {
            return "redirect:/comments/" + eventId + "/chat-room";
        }
    }


}
