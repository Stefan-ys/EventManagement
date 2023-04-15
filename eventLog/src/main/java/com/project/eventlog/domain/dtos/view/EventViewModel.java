package com.project.eventlog.domain.dtos.view;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EventViewModel {

    private long id;
    private String name;
    private String description;
    private String location;
    private String eventDateTime;
    private long hostId;
    private String hostUsername;
    private String category;
    private String numberOfParticipants;
    private BigDecimal price;
    private int duration;
    private String status;

    private List<PictureViewModel> pictures = new ArrayList<>();

    public EventViewModel() {
    }

    public long getId() {
        return id;
    }

    public EventViewModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EventViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public EventViewModel setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getEventDateTime() {
        return eventDateTime;
    }

    public EventViewModel setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
        return this;
    }

    public long getHostId() {
        return hostId;
    }

    public EventViewModel setHostId(long hostId) {
        this.hostId = hostId;
        return this;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public EventViewModel setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public EventViewModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public EventViewModel setNumberOfParticipants(String numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EventViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public EventViewModel setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public EventViewModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public List<PictureViewModel> getPictures() {
        return pictures;
    }

    public EventViewModel setPictures(List<PictureViewModel> pictures) {
        this.pictures = pictures;
        return this;
    }
}
