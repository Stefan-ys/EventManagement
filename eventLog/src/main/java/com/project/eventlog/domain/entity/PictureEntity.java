package com.project.eventlog.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;

    @ManyToOne
    private EventsEntity event;

    public PictureEntity() {
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public PictureEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public PictureEntity setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public PictureEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public EventsEntity getEvent() {
        return event;
    }

    public PictureEntity setEvent(EventsEntity event) {
        this.event = event;
        return this;
    }
}
