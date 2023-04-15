package com.project.eventlog.service;

import com.project.eventlog.domain.dtos.service.UserRegistrationServiceModel;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.domain.entity.UserRoleEntity;
import com.project.eventlog.domain.enums.LocationEnum;
import com.project.eventlog.domain.enums.RoleEnum;
import com.project.eventlog.repository.UserRepository;
import com.project.eventlog.repository.UserRoleRepository;
import com.project.eventlog.service.impl.ApplicationUserDetailsService;
import com.project.eventlog.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRoleRepository userRoleRepository;
    @InjectMocks
    private ApplicationUserDetailsService applicationUserDetailsService;
    @InjectMocks
    private UserServiceImpl userServiceT;

    @Test
    public void testRegisterUser() {
        // Mock data
        UserRegistrationServiceModel serviceModel = new UserRegistrationServiceModel();
        serviceModel.setUsername("johnDoe");
        serviceModel.setPassword("password");
        serviceModel.setImageUrl("https://example.com/image.jpg");
        serviceModel.setEmail("john@example.com");
        serviceModel.setLocation(LocationEnum.PERNIK);
        serviceModel.setFirstName("John");
        serviceModel.setLastName("Doe");
        serviceModel.setPhoneNumber("1234567890");

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(RoleEnum.USER);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(serviceModel.getUsername());
        userEntity.setPassword("encoded_password");
        userEntity.setImageUrl(serviceModel.getImageUrl());
        userEntity.setEmail(serviceModel.getEmail());
        userEntity.setLocation(serviceModel.getLocation());
        userEntity.setFirstName(serviceModel.getFirstName());
        userEntity.setLastName(serviceModel.getLastName());
        userEntity.setPhoneNumber(serviceModel.getPhoneNumber());
        userEntity.setRoles(Collections.singleton(userRoleEntity));

        when(userRoleRepository.findByRole(RoleEnum.USER)).thenReturn(userRoleEntity);
        when(passwordEncoder.encode(serviceModel.getPassword())).thenReturn("encoded_password");

        userServiceT.registerUser(serviceModel);

        verify(userRepository).save(argThat(user -> user.getUsername().equals(serviceModel.getUsername())));

        UserDetails principal = applicationUserDetailsService.loadUserByUsername(userEntity.getUsername());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(principal.getUsername(), authentication.getPrincipal());
        assertEquals(userEntity.getPassword(), authentication.getCredentials());
        assertEquals(principal.getAuthorities(), authentication.getAuthorities());
    }
}
