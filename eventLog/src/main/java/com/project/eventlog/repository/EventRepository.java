package com.project.eventlog.repository;

import com.project.eventlog.domain.entity.EventsEntity;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.domain.enums.EventStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventsEntity, Long> {

    List<EventsEntity> findEventsEntitiesByHostId(Long hostId);

    @Query("SELECT e FROM EventsEntity e ORDER BY "
            + "CASE WHEN :sortBy = 'name' THEN e.name END ASC, "
            + "CASE WHEN :sortBy = 'date' THEN e.eventDateTime END ASC, "
            + "CASE WHEN :sortBy = 'location' THEN e.location END ASC, "
            + "CASE WHEN :sortBy = 'host' THEN e.host.username END ASC, "
            + "CASE WHEN :sortBy = 'categoriy' THEN e.category END ASC")
    List<EventsEntity> findAllEventsInOrder(@Param("sortBy") String sortBy);

    @Query("SELECT e FROM EventsEntity e WHERE e.status = :filter " +
            "ORDER BY "
            + "CASE WHEN :sortBy = 'name' THEN e.name END ASC, "
            + "CASE WHEN :sortBy = 'date' THEN e.eventDateTime END ASC, "
            + "CASE WHEN :sortBy = 'location' THEN e.location END ASC, "
            + "CASE WHEN :sortBy = 'host' THEN e.host.username END ASC, "
            + "CASE WHEN :sortBy = 'category' THEN e.category END ASC")
    List<EventsEntity> findByActiveStatusInOrder(@Param("sortBy") String sortBy, @Param("filter") EventStatusEnum filter);

    @Query("SELECT p " +
            "FROM EventsEntity e JOIN e.participants p " +
            "WHERE e.id = :eventId AND p.username = :username")
    Optional<UserEntity> findParticipantByUsernameAndEventId(Long eventId, String username);

    List<EventsEntity> findAllByHostId(Long userId);

}
