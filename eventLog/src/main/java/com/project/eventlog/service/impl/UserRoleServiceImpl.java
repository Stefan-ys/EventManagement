package com.project.eventlog.service.impl;

import com.project.eventlog.domain.entity.UserRoleEntity;
import com.project.eventlog.domain.enums.RoleEnum;
import com.project.eventlog.repository.UserRoleRepository;
import com.project.eventlog.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void initializeRoles() {
        if (userRoleRepository.count() == 0) {
            for (RoleEnum roleEnum : RoleEnum.values()) {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setRole(roleEnum);
                userRoleRepository.save(userRoleEntity);
            }
        }
    }
}
