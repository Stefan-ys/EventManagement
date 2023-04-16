package com.project.eventlog.service;

import com.project.eventlog.domain.dtos.service.CommentServiceModel;
import com.project.eventlog.domain.dtos.view.CommentViewModel;
import com.project.eventlog.domain.entity.CommentEntity;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.repository.CommentRepository;
import com.project.eventlog.repository.EventRepository;
import com.project.eventlog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EventRepository eventRepository;

    private DateTimeFormatter dateTimeFormatter;

    private CommentService commentService;

    @Test
    void testGetAllCommentsWithoutEvent() {
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        EventRepository eventRepository = Mockito.mock(EventRepository.class);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

        CommentServiceImpl commentService = new CommentServiceImpl(commentRepository, modelMapper, eventRepository, dateTimeFormatter);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1L);
        commentEntity.setAuthor(new UserEntity());
        commentEntity.setContent("Test comment");
        commentEntity.setDateTime(LocalDateTime.now());

        when(commentRepository.findAllByEventIdIsNullOrderByDateTimeDesc()).thenReturn(Collections.singletonList(commentEntity));

        List<CommentViewModel> result = commentService.getAllCommentsWithoutEvent();
        assertThat(result).isNotNull();
    }

    @Test
    void testAddComment() {
        CommentServiceModel commentServiceModel = new CommentServiceModel();
        commentServiceModel.setAuthorId(1L);
        commentServiceModel.setContent("Test comment");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1L);
        commentEntity.setContent("Test comment");
        commentEntity.setDateTime(LocalDateTime.now());
        commentEntity.setAuthor(userEntity);

        CommentViewModel commentViewModel = new CommentViewModel();
        commentViewModel.setId(1L);
        commentViewModel.setContent("Test comment");

        when(modelMapper.map(commentServiceModel, CommentEntity.class))
                .thenReturn(commentEntity);

        when(commentRepository.save(commentEntity))
                .thenReturn(commentEntity);

        when(modelMapper.map(commentEntity, CommentViewModel.class))
                .thenReturn(commentViewModel);

        commentService = new CommentServiceImpl(commentRepository, modelMapper, eventRepository, dateTimeFormatter);

        CommentViewModel result = commentService.addComment(commentServiceModel);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(commentViewModel);
    }

    @Test
    void testDeleteComment() {
        Long commentId = 1L;

        commentService = new CommentServiceImpl(commentRepository, modelMapper, eventRepository, dateTimeFormatter);

        commentService.deleteComment(commentId);

        verify(commentRepository).deleteById(commentId);
    }
}