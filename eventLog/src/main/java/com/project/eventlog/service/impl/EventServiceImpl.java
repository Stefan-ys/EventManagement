package com.project.eventlog.service.impl;

import com.project.eventlog.domain.dtos.service.EventServiceModel;
import com.project.eventlog.domain.dtos.view.EventViewModel;
import com.project.eventlog.domain.dtos.view.PictureViewModel;
import com.project.eventlog.domain.dtos.view.UserViewModel;
import com.project.eventlog.domain.entity.EventsEntity;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.domain.enums.EventStatusEnum;
import com.project.eventlog.repository.EventRepository;
import com.project.eventlog.repository.PictureRepository;
import com.project.eventlog.repository.UserRepository;
import com.project.eventlog.service.EventService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        if (filter.equalsIgnoreCase("CANCELLED")) {
            eventStatusEnum = EventStatusEnum.CANCELLED;
        }


        return eventRepository
                .findByActiveStatusInOrder(sort.toLowerCase(), eventStatusEnum)
                .stream()
                .map(this::convertEntityToViewModel)
                .collect(Collectors.toList());
    }


    @Override
    public EventViewModel getEventById(Long id) {
        EventsEntity event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such event"));
        return convertEntityToViewModel(event);
    }

    @Override
    @Transactional
    public EventViewModel addEvent(EventServiceModel eventServiceModel, String username) {
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username).orElseThrow();

        EventsEntity event = modelMapper.map(eventServiceModel, EventsEntity.class);
        event
                .setHost(userEntity)
                .setStatus(EventStatusEnum.ACTIVE);

        eventRepository.save(event);

        return convertEntityToViewModel(event);

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
    public void cancelEvent(Long eventId) {
        EventsEntity eventsEntity = eventRepository.findById(eventId).orElseThrow();
        eventsEntity.setStatus(eventsEntity
                .getStatus()
                .equals(EventStatusEnum.CANCELLED) ? EventStatusEnum.ACTIVE : EventStatusEnum.CANCELLED);

        eventRepository.save(eventsEntity);

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






    private EventViewModel convertEntityToViewModel(EventsEntity event) {
        updateEventStatus(event);
        List<PictureViewModel> pictureViewModels = pictureRepository.findAllByEventId(event.getId())
                .stream().map(p -> modelMapper.map(p, PictureViewModel.class)).toList();

        EventViewModel viewModel = modelMapper.map(event, EventViewModel.class);
        viewModel
                .setHostUsername(event.getHost().getUsername())
                .setEventDateTime(dateTimeFormatter.format(event.getEventDateTime()))
                .setNumberOfParticipants(String.format("%d / %d", event.getParticipants().size(), event.getNumberOfParticipants()))
                .setPictures(pictureViewModels);
        return viewModel;
    }

    private void updateEventStatus(EventsEntity eventsEntity) {
        if (eventsEntity.getStatus().equals(EventStatusEnum.CANCELLED)) {
            return;
        }
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
