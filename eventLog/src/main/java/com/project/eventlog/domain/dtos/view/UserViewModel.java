package com.project.eventlog.domain.dtos.view;

public class UserViewModel {
    private long id;
    private String username;
    private String imageUrl;
    private String location;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;

    private int numberOfEventsHosted;

    private int numberOfEventsJoined;

    public UserViewModel() {
    }

    public long getId() {
        return id;
    }

    public UserViewModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public UserViewModel setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserViewModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserViewModel setRole(String role) {
        this.role = role;
        return this;
    }

    public int getNumberOfEventsHosted() {
        return numberOfEventsHosted;
    }

    public UserViewModel setNumberOfEventsHosted(int numberOfEventsHosted) {
        this.numberOfEventsHosted = numberOfEventsHosted;
        return this;
    }

    public int getNumberOfEventsJoined() {
        return numberOfEventsJoined;
    }

    public UserViewModel setNumberOfEventsJoined(int numberOfEventsJoined) {
        this.numberOfEventsJoined = numberOfEventsJoined;
        return this;
    }
}
