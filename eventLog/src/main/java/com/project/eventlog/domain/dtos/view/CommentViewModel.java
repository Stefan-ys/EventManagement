package com.project.eventlog.domain.dtos.view;

import java.util.ArrayList;
import java.util.List;

public class CommentViewModel {
    private long id;
    private String authorUsername;
    private long authorId;
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

    public long getAuthorId() {
        return authorId;
    }

    public CommentViewModel setAuthorId(long authorId) {
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
