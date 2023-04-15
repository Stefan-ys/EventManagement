package com.project.eventlog.service.impl;

import com.project.eventlog.domain.dtos.service.EventServiceModel;
import com.project.eventlog.domain.dtos.view.EventViewModel;
import com.project.eventlog.domain.dtos.view.PictureViewModel;
import com.project.eventlog.domain.dtos.view.UserViewModel;
import com.project.eventlog.domain.entity.EventsEntity;
import com.project.eventlog.domain.entity.PictureEntity;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.domain.enums.EventStatusEnum;
import com.project.eventlog.repository.EventRepository;
import com.project.eventlog.repository.PictureRepository;
import com.project.eventlog.repository.UserRepository;
import com.project.eventlog.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    private final DateTimeFormatter dateTimeFormatter;
    private final PictureRepository pictureRepository;

    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper,
                            UserRepository userRepository, DateTimeFormatter dateTimeFormatter,
                            PictureRepository pictureRepository) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.dateTimeFormatter = dateTimeFormatter;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public List<EventViewModel> getAllEvents(String sort, String filter) {
        if (filter.equalsIgnoreCase("ALL")) {
            return eventRepository
                    .findAllEventsInOrder(sort)
                    .stream()
                    .map(this::convertEntityToViewModel)
                    .collect(Collectors.toList());
        }
        EventStatusEnum eventStatusEnum = EventStatusEnum.ACTIVE;
        if (filter.equalsIgnoreCase("FULL")) {
            eventStatusEnum = EventStatusEnum.FULL;
        }
        if (filter.equalsIgnoreCase("COMPLETED")) {
            eventStatusEnum = EventStatusEnum.COMPLETED;
        }


        return eventRepository
                .findByActiveStatusInOrder(sort.toLowerCase(), eventStatusEnum)
                .stream()
                .map(this::convertEntityToViewModel)
                .collect(Collectors.toList());
    }


    @Override
    public long addEvent(EventServiceModel eventServiceModel, String username) {
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username).orElseThrow();
        EventsEntity event = modelMapper.map(eventServiceModel, EventsEntity.class);
        event

                .setHost(userEntity)
                .setStatus(EventStatusEnum.ACTIVE);

        return eventRepository.save(event).getId();

    }


    @Override
    public EventViewModel getEventById(Long id) {
        EventsEntity event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such event"));
        return convertEntityToViewModel(event);
    }

    @Override
    public void deleteEventById(Long eventId) {
        eventRepository.deleteById(eventId);
    }


    @Override
    public void joinEvent(Long eventId, String username) {
        EventsEntity eventsEntity = getEventEntityById(eventId);
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new IllegalArgumentException("Invalid user."));
        if (eventsEntity.getParticipants().contains(userEntity)) {
            throw new IllegalStateException("You have already joined this event.");
        }
        if (eventsEntity.getNumberOfParticipants() <= eventsEntity.getParticipants().size()) {
            throw new IllegalStateException("Maximum number of participants reached.");
        }
        eventsEntity.getParticipants().add(userEntity);
        eventRepository.save(eventsEntity);
    }

    @Override
    public void leaveEvent(Long eventId, String username) {
        EventsEntity eventEntity = getEventEntityById(eventId);
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new IllegalArgumentException("Invalid user ID."));

        for (UserEntity user : eventEntity.getParticipants()) {
            if (user.getId().equals(userEntity.getId())) {
                eventEntity.getParticipants().remove(user);
                eventRepository.save(eventEntity);
                return;
            }
        }
    }

    @Override
    public List<UserViewModel> getParticipantsFromEvent(Long eventId) {
        EventsEntity eventEntity = getEventEntityById(eventId);

        return eventEntity.getParticipants()
                .stream()
                .map(userEntity -> modelMapper.map(userEntity, UserViewModel.class)).collect(Collectors.toList());

    }

    @Override
    public List<EventViewModel> getEventsWhereUserIsHost(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID."))
                .getEventsHosted()
                .stream().map(this::convertEntityToViewModel)
                .toList();
    }

    @Override
    public List<EventViewModel> getEventsWhereUserIsJoined(long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID."))
                .getEventsJoined()
                .stream().map(this::convertEntityToViewModel)
                .toList();
    }

    @Override
    public boolean isEventParticipant(long eventId, String username) {
        return eventRepository.findParticipantByUsernameAndEventId(eventId, username).isPresent();
    }

    @Override
    public void addPictureToEvent(PictureViewModel pictureServiceModel) {
        EventsEntity eventsEntity = eventRepository.findById(pictureServiceModel.getEventId()).orElseThrow(() -> new IllegalArgumentException("No such event Id"));
        UserEntity userEntity = userRepository.findById(pictureServiceModel.getAuthorId()).orElseThrow(() -> new IllegalArgumentException("No such user Id"));
        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setAuthor(userEntity)
                .setEvent(eventsEntity)
                .setCreationDate(LocalDate.now())
                .setImageUrl(pictureServiceModel.getImageUrl());

        eventsEntity.getPictures().add(pictureEntity);
        userEntity.getPictures().add(pictureEntity);

        pictureRepository.save(pictureEntity);
        eventRepository.save(eventsEntity);
        userRepository.save(userEntity);
    }

    @Override
    public List<PictureViewModel> getPicturesFromEvent(Long eventId) {
        EventsEntity eventEntity = eventRepository.findById(eventId).orElseThrow();
        return eventEntity.getPictures()
                .stream()
                .map(picture -> modelMapper.map(picture, PictureViewModel.class))
                .collect(Collectors.toList());

    }


    private EventViewModel convertEntityToViewModel(EventsEntity event) {
        updateEventStatus(event);

        EventViewModel viewModel = modelMapper.map(event, EventViewModel.class);

        viewModel
                .setHostId(event.getHost().getId())
                .setHostUsername(event.getHost().getUsername())
                .setLocation(event.getLocation().name())
                .setCategory(event.getCategory().name())
                .setStatus(event.getStatus().name())
                .setEventDateTime(dateTimeFormatter.format(event.getEventDateTime()))
                .setNumberOfParticipants(String.format("%d / %d", event.getParticipants().size(), event.getNumberOfParticipants()))
                .setPictures(event.getPictures().stream().map(p -> modelMapper.map(p, PictureViewModel.class)).toList());
        return viewModel;
    }

    private void updateEventStatus(EventsEntity eventsEntity) {
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        if (eventsEntity.getEventDateTime().isBefore(localDateTimeNow)) {
            eventsEntity.setStatus(EventStatusEnum.COMPLETED);
        } else if (eventsEntity.getParticipants().size() >= eventsEntity.getNumberOfParticipants()) {
            eventsEntity.setStatus(EventStatusEnum.FULL);
        } else {
            eventsEntity.setStatus(EventStatusEnum.ACTIVE);
        }
        eventRepository.save(eventsEntity);

    }


    private EventsEntity getEventEntityById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Invalid event ID."));
    }
}
