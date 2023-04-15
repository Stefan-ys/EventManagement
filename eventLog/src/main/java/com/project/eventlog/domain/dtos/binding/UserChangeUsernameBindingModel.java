package com.project.eventlog.domain.dtos.binding;

import jakarta.validation.constraints.*;

public class UserChangeUsernameBindingModel {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 20, message = "Username length must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    private String username;

    @NotBlank(message = "Old password is required")
    private String oldPassword;
    @NotBlank(message = "New password is required")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    private String newPassword;

    @NotBlank(message = "Confirm new password is required")
    @Size(min = 4, max = 20, message = "Confirm password must be between 4 and 20 characters")
    private String confirmNewPassword;

    public UserChangeUsernameBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserChangeUsernameBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public UserChangeUsernameBindingModel setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserChangeUsernameBindingModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public UserChangeUsernameBindingModel setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}
