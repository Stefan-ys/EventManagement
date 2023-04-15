package com.project.eventlog.domain.dtos.binding;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


public class PictureBindingModel {

    @URL(message = "Invalid URL format")
    @NotBlank(message = "Image URL cannot be blank")
    private String imageUrl;

    public PictureBindingModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PictureBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
