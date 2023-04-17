package com.project.eventlog.service;

import com.project.eventlog.domain.dtos.service.CommentServiceModel;
import com.project.eventlog.domain.dtos.view.CommentViewModel;

import java.util.List;

public interface CommentService {
    List<CommentViewModel> getAllCommentsWithoutEvent();

    List<CommentViewModel> getAllCommentsByEvent(Long eventId);

    CommentViewModel addComment(CommentServiceModel commentServiceModel);


    void deleteComment(Long commentId);


}
