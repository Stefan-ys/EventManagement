package com.project.eventlog.service;

import com.project.eventlog.domain.dtos.service.CommentServiceModel;
import com.project.eventlog.domain.dtos.view.CommentViewModel;
import com.project.eventlog.domain.entity.CommentEntity;
import com.project.eventlog.domain.entity.EventsEntity;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.repository.CommentRepository;
import com.project.eventlog.repository.EventRepository;
import com.project.eventlog.repository.UserRepository;
import com.project.eventlog.service.impl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentServiceImpl commentService;


    private UserEntity user;
    private EventsEntity event;
    private CommentEntity comment;

    @BeforeEach
    public void setUp() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        commentService = new CommentServiceImpl(commentRepository, modelMapper, eventRepository, dateTimeFormatter, userRepository);
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
    public void testGetAllCommentsByEvent() {
        List<CommentEntity> comments = new ArrayList<>();
        comments.add(comment);

        when(commentRepository.findAllByEventIdOrderByDateTimeDesc(1L)).thenReturn(comments);

        List<CommentViewModel> commentViewModels = commentService.getAllCommentsByEvent(1L);

        Assert.assertEquals(1, commentViewModels.size());
        CommentViewModel commentViewModel = commentViewModels.get(0);
        Assert.assertEquals(Optional.ofNullable(comment.getId()), Optional.ofNullable(commentViewModel.getId()));
        Assert.assertEquals(comment.getAuthor().getUsername(), commentViewModel.getAuthorUsername());
        Assert.assertEquals(comment.getAuthor().getId(), commentViewModel.getAuthorId());
        Assert.assertEquals(comment.getContent(), commentViewModel.getContent());
        Assert.assertEquals("01 Jan 2024 12:00", commentViewModel.getDateTime());
    }

    @Test
    public void testAddCommentWithoutEvent() {
        CommentServiceModel commentServiceModel = new CommentServiceModel();
        commentServiceModel.setAuthorId(user.getId());
        commentServiceModel.setContent("test comment");
        commentServiceModel.setEventId(event.getId());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        CommentViewModel commentViewModel = commentService.addComment(commentServiceModel);

        Assert.assertNotNull(commentViewModel);
        Assert.assertEquals(comment.getAuthor().getUsername(), commentViewModel.getAuthorUsername());
        Assert.assertEquals(comment.getAuthor().getId(), commentViewModel.getAuthorId());
        Assert.assertEquals(comment.getContent(), commentViewModel.getContent());
        Assert.assertEquals(comment.getDateTime().toString(), commentViewModel.getDateTime());

        Mockito.verify(commentRepository).save(Mockito.any(CommentEntity.class));
    }

    @Test
    public void testAddCommentWithEvent() {

        CommentServiceModel commentServiceModel = new CommentServiceModel();
        commentServiceModel.setAuthorId(user.getId());
        commentServiceModel.setContent("test comment");
        commentServiceModel.setEventId(event.getId());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        CommentViewModel commentViewModel = commentService.addComment(commentServiceModel);

        Assert.assertEquals(comment.getAuthor().getUsername(), commentViewModel.getAuthorUsername());
        Assert.assertEquals(comment.getAuthor().getId(), commentViewModel.getAuthorId());
        Assert.assertEquals(comment.getContent(), commentViewModel.getContent());
        Assert.assertEquals(comment.getDateTime().toString(), commentViewModel.getDateTime());
        Assert.assertEquals(comment.getEvent().getId(), event.getId());

        Mockito.verify(commentRepository).save(Mockito.any(CommentEntity.class));
    }

    @Test
    void testDeleteComment() {
        Long commentId = 1L;
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(commentId);

        commentService.deleteComment(commentId);

        Mockito.verify(commentRepository).deleteById(commentId);

    }
}

