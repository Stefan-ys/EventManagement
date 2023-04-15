package com.project.eventlog.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @NotNull
    @ManyToOne
    private UserEntity author;


    @ManyToOne
    private EventsEntity event;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 1000)
    private String content;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> replies = new ArrayList<>();

    @ManyToOne
    private CommentEntity parentComment;

    public CommentEntity() {
    }

    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public EventsEntity getEvent() {
        return event;
    }

    public CommentEntity setEvent(EventsEntity event) {
        this.event = event;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public CommentEntity setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentEntity setContent(String content) {
        this.content = content;
        return this;
    }


    public List<CommentEntity> getReplies() {
        return replies;
    }

    public CommentEntity setReplies(List<CommentEntity> replies) {
        this.replies = replies;
        return this;
    }

    public CommentEntity getParentComment() {
        return parentComment;
    }

    public CommentEntity setParentComment(CommentEntity parentComment) {
        this.parentComment = parentComment;
        return this;
    }
}
