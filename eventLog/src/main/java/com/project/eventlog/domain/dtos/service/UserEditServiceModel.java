package com.project.eventlog.domain.dtos.service;

import com.project.eventlog.domain.enums.LocationEnum;

public class UserEditServiceModel {
    private String imageUrl;
    private LocationEnum location;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public UserEditServiceModel() {
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public UserEditServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public UserEditServiceModel setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEditServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEditServiceModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}

