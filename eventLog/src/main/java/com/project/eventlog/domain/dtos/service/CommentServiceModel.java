package com.project.eventlog.domain.dtos.service;

import java.time.LocalDateTime;

public class CommentServiceModel {
    private long authorId;
    private Long eventId;
    private LocalDateTime dateTime;
    private String content;

    public CommentServiceModel() {
    }


    public long getAuthorId() {
        return authorId;
    }

    public CommentServiceModel setAuthorId(long authorId) {
        this.authorId = authorId;
        return this;
    }

    public Long getEventId() {
        return eventId;
    }

    public CommentServiceModel setEventId(Long eventId) {
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

}
