package com.project.eventlog.domain.dtos.binding;

import com.project.eventlog.domain.enums.LocationEnum;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public class UserEditBindingModel {

    @URL(message = "Invalid URL format")
    private String imageUrl;

    @NotNull(message = "Location cannot be null")
    private LocationEnum location;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 1, max = 20, message = "First name length must be between {min} and {max} characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 1, max = 20, message = "Last name length must be between {min} and {max} characters")
    private String lastName;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email cannot be blank")
    @Size(min = 4, max = 50, message = "Email length must be between {min} and {max} characters")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 4, max = 20, message = "Phone number length must be between {min} and {max} characters")
    @Pattern(regexp = "^[0-9-+() ]*$", message = "Phone number can only contain numbers, hyphens, plus signs, parentheses, and spaces")
    private String phoneNumber;

    public UserEditBindingModel() {
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public UserEditBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public UserEditBindingModel setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEditBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEditBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
