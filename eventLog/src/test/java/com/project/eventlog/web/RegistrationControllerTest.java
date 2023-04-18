package com.project.eventlog.web;

import com.project.eventlog.domain.dtos.binding.UserRegisterBindingModel;
import com.project.eventlog.domain.dtos.view.UserViewModel;
import com.project.eventlog.domain.entity.UserEntity;
import com.project.eventlog.domain.enums.LocationEnum;
import com.project.eventlog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Test
    void testGetRegister() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-auth-register"));
    }

    @Test
    void testPostRegisterValidData() throws Exception {
        UserRegisterBindingModel bindingModel = createBindingModel();

        mockMvc.perform(post("/users/register")
                        .param("username", bindingModel.getUsername())
                        .param("imageUrl", bindingModel.getImageUrl())
                        .param("location", bindingModel.getLocation().toString())
                        .param("firstName", bindingModel.getFirstName())
                        .param("lastName", bindingModel.getLastName())
                        .param("email", bindingModel.getEmail())
                        .param("phoneNumber", bindingModel.getPhoneNumber())
                        .param("password", bindingModel.getPassword())
                        .param("confirmPassword", bindingModel.getConfirmPassword())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));


        Optional<UserEntity> userEntity = userRepository.findByUsername(bindingModel.getUsername());
        if (userEntity.isPresent()) {
            UserViewModel user = modelMapper.map(userEntity.get(), UserViewModel.class);
            assertNotNull(user);
            assertEquals(bindingModel.getUsername(), user.getUsername());
            assertEquals(bindingModel.getLocation(), user.getLocation());
            assertEquals(bindingModel.getFirstName(), user.getFirstName());
            assertEquals(bindingModel.getLastName(), user.getLastName());
            assertEquals(bindingModel.getEmail(), user.getEmail());
            assertEquals(bindingModel.getPhoneNumber(), user.getPhoneNumber());
        } else {
            fail("User not found");
        }


    }

    @Test
    void testPostRegisterInvalidData() throws Exception {
        UserRegisterBindingModel bindingModel = createBindingModel();
        bindingModel.setUsername("");
        bindingModel.setFirstName("");
        bindingModel.setLastName("");
        bindingModel.setEmail("");
        bindingModel.setPhoneNumber("");

        mockMvc.perform(post("/users/register")
                        .param("username", bindingModel.getUsername())
                        .param("imageUrl", bindingModel.getImageUrl())
                        .param("location", bindingModel.getLocation().toString())
                        .param("firstName", bindingModel.getFirstName())
                        .param("lastName", bindingModel.getLastName())
                        .param("email", bindingModel.getEmail())
                        .param("phoneNumber", bindingModel.getPhoneNumber())
                        .param("password", bindingModel.getPassword())
                        .param("confirmPassword", bindingModel.getConfirmPassword())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user-auth-register"))
                .andExpect(model().attributeHasFieldErrors("userRegisterBindingModel", "username"))
                .andExpect(model().attributeHasFieldErrors("userRegisterBindingModel", "firstName"))
                .andExpect(model().attributeHasFieldErrors("userRegisterBindingModel", "lastName"))
                .andExpect(model().attributeHasFieldErrors("userRegisterBindingModel", "email"))
                .andExpect(model().attributeHasFieldErrors("userRegisterBindingModel", "phoneNumber"));

        assertNull(userRepository.findByUsername(bindingModel.getUsername()));
    }

    private UserRegisterBindingModel createBindingModel() {
        UserRegisterBindingModel bindingModel = new UserRegisterBindingModel();
        bindingModel.setUsername("user");
        bindingModel.setLocation(LocationEnum.SOFIA);
        bindingModel.setFirstName("John");
        bindingModel.setLastName("Doe");
        bindingModel.setEmail("john.doe@example.com");
        bindingModel.setPhoneNumber("1234567890");
        bindingModel.setPassword("password");
        bindingModel.setConfirmPassword("password");
        return bindingModel;
    }
}