package com.project.eventlog.repository;

import com.project.eventlog.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameIgnoreCase(String userName);

    Optional<UserEntity> findByEmailIgnoreCase(String userEmail);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByUsername(String username);

}
