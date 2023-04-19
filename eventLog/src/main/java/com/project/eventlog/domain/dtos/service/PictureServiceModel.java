package com.project.eventlog.domain.dtos.service;

import java.time.LocalDate;

public class PictureServiceModel {
    private String imageUrl;
    private LocalDate creationDate;
    private long authorId;
    private long eventId;

    public PictureServiceModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PictureServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public PictureServiceModel setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public long getAuthorId() {
        return authorId;
    }

    public PictureServiceModel setAuthorId(long authorId) {
        this.authorId = authorId;
        return this;
    }

    public long getEventId() {
        return eventId;
    }

    public PictureServiceModel setEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }
}
