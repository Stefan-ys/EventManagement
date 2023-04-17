package com.project.eventlog.service;

import com.project.eventlog.domain.dtos.service.EventServiceModel;
import com.project.eventlog.domain.dtos.view.EventViewModel;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.repository.EventRepository;
import com.project.eventlog.repository.UserRepository;
import com.project.eventlog.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    public void testAddEvent() {
        // Arrange
        String username = "testuser";
        UserEntity user = new UserEntity();

        Mockito.when(userRepository.findByUsername(username)).thenReturn(user);
        EventServiceModel eventServiceModel = new EventServiceModel();

        // Act

        EventViewModel result = eventService.addEvent(eventServiceModel, username);

        // Assert
        assertNotNull(result);
        assertEquals(eventServiceModel.getTitle(), result.getTitle());
        assertEquals(eventServiceModel.getDescription(), result.getDescription());
        assertEquals(eventServiceModel.getStartDate(), result.getStartDate());
        assertEquals(eventServiceModel.getEndDate(), result.getEndDate());
        assertEquals(eventServiceModel.getLocation(), result.getLocation());
        assertEquals(user.getUsername(), result.getUsername());
    }
}