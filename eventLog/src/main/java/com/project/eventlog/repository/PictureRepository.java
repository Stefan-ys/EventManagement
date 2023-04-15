package com.project.eventlog.repository;

import com.project.eventlog.domain.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {
    List<PictureEntity> findAllByAuthorId(Long userId);

}
