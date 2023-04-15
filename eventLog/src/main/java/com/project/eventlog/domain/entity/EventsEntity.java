package com.project.eventlog.domain.entity;

import com.project.eventlog.domain.enums.CategoryEnum;
import com.project.eventlog.domain.enums.EventStatusEnum;
import com.project.eventlog.domain.enums.LocationEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
public class EventsEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LocationEnum location;
    @Column(nullable = false)
    private LocalDateTime eventDateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity host;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    @Column(nullable = false)
    private Integer numberOfParticipants;
    @Column
    private BigDecimal price;
    @Column
    private Integer duration;
    @Enumerated(EnumType.STRING)
    private EventStatusEnum status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_user",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> participants = new HashSet<>();
    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private Set<CommentEntity> comments = new HashSet<>();
    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private Set<PictureEntity> pictures = new HashSet<>();

    public EventsEntity() {
    }

    public String getName() {
        return name;
    }

    public EventsEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventsEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public EventsEntity setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public EventsEntity setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
        return this;
    }

    public UserEntity getHost() {
        return host;
    }

    public EventsEntity setHost(UserEntity host) {
        this.host = host;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public EventsEntity setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public EventsEntity setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EventsEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public EventsEntity setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public EventStatusEnum getStatus() {
        return status;
    }

    public EventsEntity setStatus(EventStatusEnum status) {
        this.status = status;
        return this;
    }

    public Set<UserEntity> getParticipants() {
        return participants;
    }

    public EventsEntity setParticipants(Set<UserEntity> participants) {
        this.participants = participants;
        return this;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public EventsEntity setComments(Set<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public EventsEntity setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }
}
