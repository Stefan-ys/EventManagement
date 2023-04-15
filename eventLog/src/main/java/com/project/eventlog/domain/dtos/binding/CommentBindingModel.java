package com.project.eventlog.domain.dtos.binding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CommentBindingModel {

    @NotNull(message = "Content cannot be null.")
    @Size(min = 1, max = 500, message = "Content must be between 1 and 500 characters long.")
    private String content;

    private Long parentCommentId;

    public CommentBindingModel() {
    }


    public String getContent() {
        return content;
    }

    public CommentBindingModel setContent(String content) {
        this.content = content;
        return this;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public CommentBindingModel setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
        return this;
    }
}
