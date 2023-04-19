package com.project.eventlog.service;


import com.project.eventlog.domain.dtos.service.PictureServiceModel;
import com.project.eventlog.domain.dtos.view.PictureViewModel;

import java.util.List;

public interface PictureService {

    List<PictureViewModel> getPicturesFromEvent(Long eventId);

    void addPictureToEvent(PictureServiceModel pictureServiceModel);

    void deletePicture(Long pictureId);
}
