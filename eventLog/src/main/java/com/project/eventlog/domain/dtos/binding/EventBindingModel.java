package com.project.eventlog.domain.dtos.binding;

import com.project.eventlog.domain.enums.CategoryEnum;
import com.project.eventlog.domain.enums.LocationEnum;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventBindingModel {

    @NotBlank(message = "Event name cannot be blank")
    @Size(min = 3, max = 50, message = "Event name must be between 3 and 50 characters")
    private String name;
    @Size(max = 500, message = "Event description cannot exceed 500 characters")
    private String description;
    @NotNull(message = "Location cannot be null")
    private LocationEnum location;

    @NotNull(message = "Event date cannot be null")
    @FutureOrPresent(message = "Event date time must be in the future or present")
    private LocalDateTime eventDateTime;
    @NotNull(message = "Category cannot be null")
    private CategoryEnum category;

    @NotNull
    @Min(value = 2, message = "Number of participants cannot be negative")
    private Integer numberOfParticipants;
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private BigDecimal price;
    @Min(value = 1, message = "Duration must be at least 1 hour")
    @Max(value = 24, message = "Duration cannot exceed 24 hours")
    private Integer duration;

    public EventBindingModel() {
    }

    public String getName() {
        return name;
    }

    public EventBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public EventBindingModel setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public EventBindingModel setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
        return this;
    }


    public CategoryEnum getCategory() {
        return category;
    }

    public EventBindingModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public EventBindingModel setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EventBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public EventBindingModel setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

}
