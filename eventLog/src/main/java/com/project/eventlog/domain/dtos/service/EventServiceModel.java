package com.project.eventlog.domain.dtos.service;

import com.project.eventlog.domain.enums.CategoryEnum;
import com.project.eventlog.domain.enums.EventStatusEnum;
import com.project.eventlog.domain.enums.LocationEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventServiceModel {

    private String name;
    private String description;
    private LocationEnum location;
    private LocalDateTime eventDateTime;
    private CategoryEnum category;
    private int numberOfParticipants;
    private BigDecimal price;
    private int duration;
    private EventStatusEnum status;

    public EventServiceModel() {
    }

    public String getName() {
        return name;
    }

    public EventServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public EventServiceModel setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public EventServiceModel setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public EventServiceModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public EventServiceModel setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EventServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public EventServiceModel setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public EventStatusEnum getStatus() {
        return status;
    }

    public EventServiceModel setStatus(EventStatusEnum status) {
        this.status = status;
        return this;
    }
}
