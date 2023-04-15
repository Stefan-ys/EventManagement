package com.project.eventlog.repository;

import com.project.eventlog.domain.entity.UserRoleEntity;
import com.project.eventlog.domain.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    UserRoleEntity findByRole(RoleEnum role);
}
