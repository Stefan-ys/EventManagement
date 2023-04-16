package com.project.eventlog.config;

import com.project.eventlog.domain.entity.CommentEntity;
import com.project.eventlog.domain.entity.EventsEntity;
import com.project.eventlog.repository.CommentRepository;
import com.project.eventlog.repository.EventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentCleanupSchedule {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public CommentCleanupSchedule(EventRepository eventRepository, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;

    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanup() {
        Pageable pageable = PageRequest.of(0, 50, Sort.by("dateTime").descending());

        List<EventsEntity> events = eventRepository.findAll();
        for (EventsEntity eventsEntity : events) {
            long eventId = eventsEntity.getId();
            List<CommentEntity> commentsToKeep = commentRepository.findNewestCommentFromEventById(eventId, pageable);

            entityManager.createQuery("DELETE FROM CommentEntity c WHERE c NOT IN :commentsToKeep AND c.event.id = :eventId")
                    .setParameter("commentsToKeep", commentsToKeep)
                    .setParameter("eventId", eventId)
                    .executeUpdate();
        }

        List<CommentEntity> commentsToKeep = commentRepository.findNewestCommentFromEventIsNull(pageable);

        entityManager.createQuery("DELETE FROM CommentEntity c WHERE c NOT IN :commentsToKeep AND c.event IS NULL")
                .setParameter("commentsToKeep", commentsToKeep)
                .executeUpdate();
    }
}
