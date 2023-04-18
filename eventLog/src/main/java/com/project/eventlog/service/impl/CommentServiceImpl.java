package com.project.eventlog.service.impl;

import com.project.eventlog.domain.dtos.service.CommentServiceModel;
import com.project.eventlog.domain.dtos.view.CommentViewModel;
import com.project.eventlog.domain.entity.CommentEntity;
import com.project.eventlog.domain.entity.EventsEntity;
import com.project.eventlog.repository.CommentRepository;
import com.project.eventlog.repository.EventRepository;
import com.project.eventlog.repository.UserRepository;
import com.project.eventlog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private final DateTimeFormatter dateTimeFormatter;
    private final UserRepository userRepository;


    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper,
                              EventRepository eventRepository, DateTimeFormatter dateTimeFormatter,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
        this.dateTimeFormatter = dateTimeFormatter;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentViewModel> getAllCommentsWithoutEvent() {
        return commentRepository.findAllByEventIdIsNullOrderByDateTimeDesc()
                .stream()
                .map(this::convertToViewModel)
                .collect(Collectors.toList());

    }

    @Override
    public List<CommentViewModel> getAllCommentsByEvent(Long eventId) {
        return commentRepository.findAllByEventIdOrderByDateTimeDesc(eventId)
                .stream()
                .map(this::convertToViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public CommentViewModel addComment(CommentServiceModel commentServiceModel) {
        CommentEntity commentEntity = new CommentEntity()
                .setAuthor(userRepository.findById(commentServiceModel.getAuthorId()).orElseThrow())
                .setContent(commentServiceModel.getContent())
                .setEvent(null)
                .setDateTime(LocalDateTime.now());

        if (commentServiceModel.getEventId() != null) {
            EventsEntity eventEntity = eventRepository.findById(commentServiceModel.getEventId()).orElseThrow();
            commentEntity.setEvent(eventEntity);
        }

        commentRepository.save(commentEntity);
        return modelMapper.map(commentEntity, CommentViewModel.class);
    }


    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);

    }


    private CommentViewModel convertToViewModel(CommentEntity commentEntity) {

        return new CommentViewModel()
                .setId(commentEntity.getId())
                .setAuthorUsername(commentEntity.getAuthor().getUsername())
                .setAuthorId(commentEntity.getAuthor().getId())
                .setContent(commentEntity.getContent())
                .setDateTime(dateTimeFormatter.format(commentEntity.getDateTime()));
    }
}