package com.project.eventlog.domain.dtos.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CommentServiceModel {
    private long id;
    private long authorId;
    private long eventId;
    private LocalDateTime dateTime;
    private String content;
    private long parentCommentId;

    public CommentServiceModel() {
    }

    public long getId() {
        return id;
    }

    public CommentServiceModel setId(long id) {
        this.id = id;
        return this;
    }

    public long getAuthorId() {
        return authorId;
    }

    public CommentServiceModel setAuthorId(long authorId) {
        this.authorId = authorId;
        return this;
    }

    public long getEventId() {
        return eventId;
    }

    public CommentServiceModel setEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public CommentServiceModel setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentServiceModel setContent(String content) {
        this.content = content;
        return this;
    }

    public long getParentCommentId() {
        return parentCommentId;
    }

    public CommentServiceModel setParentCommentId(long parentCommentId) {
        this.parentCommentId = parentCommentId;
        return this;
    }
}
