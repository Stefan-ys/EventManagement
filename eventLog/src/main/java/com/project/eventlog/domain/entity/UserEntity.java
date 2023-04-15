package com.project.eventlog.domain.entity;

import com.project.eventlog.domain.enums.LocationEnum;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LocationEnum location;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRoleEntity> roles = new HashSet<>();


    @OneToMany(mappedBy = "host", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventsEntity> eventsHosted = new HashSet<>();

    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<EventsEntity> eventsJoined = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> comments = new HashSet<>();

    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PictureEntity> pictures = new HashSet<>();

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public UserEntity setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public Set<EventsEntity> getEventsHosted() {
        return eventsHosted;
    }

    public UserEntity setEventsHosted(Set<EventsEntity> eventsHosted) {
        this.eventsHosted = eventsHosted;
        return this;
    }

    public Set<EventsEntity> getEventsJoined() {
        return eventsJoined;
    }

    public UserEntity setEventsJoined(Set<EventsEntity> eventsJoined) {
        this.eventsJoined = eventsJoined;
        return this;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public UserEntity setComments(Set<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public UserEntity setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }
}
