package com.project.eventlog.domain.dtos.view;

import java.time.LocalDate;

public class PictureViewModel {

    private long Id;
    private String imageUrl;
    private LocalDate creationDate;

    private String authorUsername;
    private long authorId;
    private long eventId;

    public PictureViewModel() {
    }

    public long getId() {
        return Id;
    }

    public PictureViewModel setId(long id) {
        Id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PictureViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public PictureViewModel setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public PictureViewModel setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public long getAuthorId() {
        return authorId;
    }

    public PictureViewModel setAuthorId(long authorId) {
        this.authorId = authorId;
        return this;
    }

    public long getEventId() {
        return eventId;
    }

    public PictureViewModel setEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }
}

