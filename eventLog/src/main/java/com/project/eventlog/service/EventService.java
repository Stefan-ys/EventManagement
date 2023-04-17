package com.project.eventlog.service;

import com.project.eventlog.domain.dtos.service.EventServiceModel;
import com.project.eventlog.domain.dtos.view.EventViewModel;
import com.project.eventlog.domain.dtos.view.PictureViewModel;
import com.project.eventlog.domain.dtos.view.UserViewModel;

import java.util.List;

public interface EventService {
    List<EventViewModel> getAllEvents(String sort, String filter);

    EventViewModel addEvent(EventServiceModel eventServiceModel, String username);

    EventViewModel getEventById(Long id);

    void deleteEventById(Long eventId);


    void joinEvent(Long eventId, String username);

    void leaveEvent(Long eventId, String username);

    List<UserViewModel> getParticipantsFromEvent(Long eventId);

    List<EventViewModel> getEventsWhereUserIsHost(long userId);

    List<EventViewModel> getEventsWhereUserIsJoined(long userId);

    boolean isEventParticipant(long eventId, String username);

    void addPictureToEvent(PictureViewModel pictureServiceModel);

    List<PictureViewModel> getPicturesFromEvent(Long eventId);

    void cancelEvent(Long eventId);
}
