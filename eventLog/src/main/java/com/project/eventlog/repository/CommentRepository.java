package com.project.eventlog.repository;

import com.project.eventlog.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c " +
            "WHERE c.event IS NULL " +
            "ORDER BY c.dateTime DESC")
    List<CommentEntity> findAllByEventIdIsNullOrderByDateTimeDesc();

    List<CommentEntity> findAllByEventIdOrderByDateTimeDesc(long eventId);

    List<CommentEntity> findAllByAuthorId(long userId);
}
