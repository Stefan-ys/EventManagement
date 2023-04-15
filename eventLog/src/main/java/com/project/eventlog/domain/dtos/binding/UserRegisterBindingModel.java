package com.project.eventlog.domain.dtos.binding;


import com.project.eventlog.domain.enums.LocationEnum;
import com.project.eventlog.validator.PasswordMatches;
import com.project.eventlog.validator.UniqueEmailAddress;
import com.project.eventlog.validator.UniqueUsername;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

@PasswordMatches
public class UserRegisterBindingModel {

    @UniqueUsername
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 20, message = "Username length must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    private String username;

    @URL(message = "Invalid URL format")
    private String imageUrl;

    @NotNull(message = "Location is required")
    private LocationEnum location;

    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 20, message = "First name must be between 1 and 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 20, message = "Last name must be between 1 and 20 characters")
    private String lastName;

    @UniqueEmailAddress
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email cannot be blank")
    @Size(min = 4, max = 50, message = "Email length must be between 4 and 50 characters")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 4, max = 20, message = "Phone number length must be between 4 and 20 characters")
    @Pattern(regexp = "^[0-9-+() ]*$", message = "Phone number can only contain numbers, hyphens, plus signs, parentheses, and spaces")
    private String phoneNumber;


    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    @Size(min = 4, max = 20, message = "Confirm password must be between 4 and 20 characters")
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserRegisterBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public UserRegisterBindingModel setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegisterBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
