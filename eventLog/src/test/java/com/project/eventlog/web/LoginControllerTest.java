package com.project.eventlog.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetLoginTest() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-auth-login"));
    }

    @Test
    void testOnFailedLoginTest
            () throws Exception {
        String username = "testuser";
        mockMvc.perform(post("/users/login-error")
                        .param(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/login"))
                .andExpect(flash().attributeExists("bad_credentials"))
                .andExpect(flash().attributeExists("username"))
                .andExpect(flash().attribute("bad_credentials", true))
                .andExpect(flash().attribute("username", username));
    }

}