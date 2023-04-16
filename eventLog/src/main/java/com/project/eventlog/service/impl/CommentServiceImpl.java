package com.project.eventlog.service.impl;

import com.project.eventlog.domain.dtos.service.CommentServiceModel;
import com.project.eventlog.domain.dtos.view.CommentViewModel;
import com.project.eventlog.domain.entity.CommentEntity;
import com.project.eventlog.repository.CommentRepository;
import com.project.eventlog.repository.EventRepository;
import com.project.eventlog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private final DateTimeFormatter dateTimeFormatter;


    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper,
                              EventRepository eventRepository, DateTimeFormatter dateTimeFormatter) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
        this.dateTimeFormatter = dateTimeFormatter;
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
    public void addComment(CommentServiceModel commentServiceModel) {
        CommentEntity commentEntity = modelMapper.map(commentServiceModel, CommentEntity.class);
        if (eventRepository.findById(commentServiceModel.getEventId()).isEmpty()) {
            commentEntity.setEvent(null);
        }
        commentRepository.save(commentEntity);
    }


    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);

    }

    @Override
    public void editComment(Long commentId, CommentServiceModel commentServiceModel) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("No such comment"));
        commentEntity.setContent(commentServiceModel.getContent());
        commentRepository.save(commentEntity);

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
