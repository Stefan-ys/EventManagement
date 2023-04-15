package com.project.eventlog.domain.dtos.service;

public class UserChangeUsernameServiceModel {

    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public UserChangeUsernameServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserChangeUsernameServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public UserChangeUsernameServiceModel setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserChangeUsernameServiceModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public UserChangeUsernameServiceModel setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}
