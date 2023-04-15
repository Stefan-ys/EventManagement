package com.project.eventlog.service;

import com.project.eventlog.domain.dtos.service.CommentServiceModel;
import com.project.eventlog.domain.dtos.view.CommentViewModel;
import com.project.eventlog.domain.entity.CommentEntity;

import java.util.List;

public interface CommentService {
    void addReply(long commentId, CommentServiceModel reply);

    List<CommentViewModel> getAllCommentsWithoutEvent();

    List<CommentViewModel> getAllCommentsByEvent(Long eventId);

    void addComment(CommentServiceModel commentServiceModel);


    void deleteComment(Long commentId);

    void editComment(Long commentId, CommentServiceModel commentServiceModel);
}
