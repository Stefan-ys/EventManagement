package com.project.eventlog.domain.dtos.view;

public class CommentViewModel {
    private long id;
    private String authorUsername;
    private Long authorId;
    private String content;
    private String dateTime;

    public CommentViewModel() {
    }

    public long getId() {
        return id;
    }

    public CommentViewModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public CommentViewModel setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public CommentViewModel setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getDateTime() {
        return dateTime;
    }

    public CommentViewModel setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}
