package com.project.eventlog.service;

import com.project.eventlog.domain.entity.CommentEntity;
import com.project.eventlog.domain.entity.EventsEntity;
import com.project.eventlog.domain.entity.PictureEntity;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.repository.CommentRepository;
import com.project.eventlog.repository.EventRepository;
import com.project.eventlog.repository.UserRepository;
import com.project.eventlog.service.impl.EventServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;


    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EventServiceImpl eventService;


    private UserEntity user;
    private EventsEntity event;

    private CommentEntity comment;

    private PictureEntity picture;


    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();

        user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        event = new EventsEntity();
        event.setId(1L);
        event.setName("testevent");
        event.setHost(user);

        comment = new CommentEntity();
        comment.setId(1L);
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setContent("test comment");
        comment.setDateTime(LocalDateTime.of(2024, 1, 1, 12, 0));
    }

    @Test
    public void getAllEventsTest() {

    }

    @Test
    public void addEventTest() {
    }

    @Test
    public void getEventByIdTest() {
    }

    @Test
    public void deleteEventByIdTest() {
    }

    @Test
    public void joinEventTest() {
    }

    @Test
    public void leaveEventTest() {
    }

    @Test
    public void getParticipantsFromEventTest() {
    }

    @Test
    public void getEventsWhereUserIsHostTest() {
    }

    @Test
    public void getEventsWhereUserIsJoinedTest() {
    }

    @Test
    public void isEventParticipantTest() {
    }

    @Test
    public void addPictureToEventTest() {
    }

    @Test
    public void getPicturesFromEventTest() {
    }

    @Test
    public void cancelEvent() {
    }

}
