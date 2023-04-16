package com.project.eventlog.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetLogin() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-auth-login"));
    }

//    @Test
//    public void testOnFailedLogin() throws Exception {
//        String username = "testuser";
//        mockMvc.perform(post("/users/login-error")
//                        .param(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username))
//                .andExpect(status().is4xxClientError())
//                .andExpect(status().reason(containsString("Invalid username or password")));
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/users/login"))
//                .andExpect(flash().attribute("bad_credentials", true))
//                .andExpect(flash().attribute("username", username));
//    }
}