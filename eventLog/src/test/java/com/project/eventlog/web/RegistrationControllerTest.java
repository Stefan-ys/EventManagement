package com.project.eventlog.web;

import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.domain.enums.LocationEnum;
import com.project.eventlog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testRegistrationWithValidData() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "testuser")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "testuser@example.com")
                        .param("phoneNumber", "+1-123-456-7890")
                        .param("location", "PERNIK")
                        .param("password", "testpassword")
                        .param("confirmPassword", "testpassword")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));

        UserEntity userEntity = userRepository.findByUsername("testuser").orElse(null);
        assertNotNull(userEntity);
        assertEquals("testuser@example.com", userEntity.getEmail());
        assertEquals("John", userEntity.getFirstName());
        assertEquals("Doe", userEntity.getLastName());
        assertEquals("+1-123-456-7890", userEntity.getPhoneNumber());
        assertEquals(LocationEnum.PERNIK, userEntity.getLocation());
    }

    @Test
    public void testRegistrationWithInvalidData() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("email", "invalidemail")
                        .param("password", "testpassword")
                        .param("confirmPassword", "wrongpassword")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("user", "email", "confirmPassword"));
    }
}