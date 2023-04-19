package com.project.eventlog.service.impl;

import com.project.eventlog.domain.dtos.service.PictureServiceModel;
import com.project.eventlog.domain.dtos.view.PictureViewModel;
import com.project.eventlog.domain.entity.EventsEntity;
import com.project.eventlog.domain.entity.PictureEntity;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.repository.EventRepository;
import com.project.eventlog.repository.PictureRepository;
import com.project.eventlog.repository.UserRepository;
import com.project.eventlog.service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, EventRepository eventRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<PictureViewModel> getPicturesFromEvent(Long eventId) {
        return pictureRepository.findAllByEventId(eventId)
                .stream()
                .map(this::convertToViewModel)
                .collect(Collectors.toList());

    }

    private PictureViewModel convertToViewModel(PictureEntity picture) {
        return new PictureViewModel()
                .setId(picture.getId())
                .setAuthorId(picture.getAuthor().getId())
                .setAuthorUsername(picture.getAuthor().getUsername())
                .setImageUrl(picture.getImageUrl())
                .setEventId(picture.getEvent().getId())
                .setCreationDate(picture.getCreationDate());
    }

    @Override
    public void addPictureToEvent(PictureServiceModel pictureServiceModel) {
        EventsEntity eventsEntity = eventRepository.findById(pictureServiceModel.getEventId()).orElseThrow(() -> new IllegalArgumentException("No such event Id"));
        UserEntity userEntity = userRepository.findById(pictureServiceModel.getAuthorId()).orElseThrow(() -> new IllegalArgumentException("No such user Id"));
        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setAuthor(userEntity)
                .setEvent(eventsEntity)
                .setCreationDate(LocalDate.now())
                .setImageUrl(pictureServiceModel.getImageUrl());

        pictureRepository.save(pictureEntity);

    }


    @Override
    public void deletePicture(Long pictureId) {
        pictureRepository.deleteById(pictureId);
    }
}
