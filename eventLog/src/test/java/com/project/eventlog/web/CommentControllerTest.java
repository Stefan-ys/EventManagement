package com.project.eventlog.web;

import com.project.eventlog.domain.dtos.binding.CommentBindingModel;
import com.project.eventlog.domain.dtos.service.CommentServiceModel;
import com.project.eventlog.domain.dtos.view.CommentViewModel;
import com.project.eventlog.service.CommentService;
import com.project.eventlog.service.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DateTimeFormatter dateTimeFormatter;


    @Test
    void testGetComments() throws Exception {
        mockMvc.perform(get("/comments/chat-room"))
                .andExpect(status().isOk());
//                .andExpect(view().name("/comments/chat-room"));
//                .andExpect(model().attributeExists("commentViewModel"))
//                .andExpect(model().attributeExists("commentBindingModel"))
//                .andExpect(model().attributeExists("isEvent"))
//                .andExpect(model().attribute("isEvent", false));
    }
    @Test
    void testAddComment() throws Exception {
        String content = "Test comment";
        CommentBindingModel commentBindingModel = new CommentBindingModel();
        commentBindingModel.setContent(content);

        mockMvc.perform(post("/comments/add-comment")
                        .flashAttr("commentBindingModel", commentBindingModel))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments/chat-room"));

        List<CommentViewModel> comments = commentService.getAllCommentsWithoutEvent();
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getContent()).isEqualTo(content);
    }

    @Test
    void testDeleteComment() throws Exception {
        CommentViewModel comment = commentService.addComment(new CommentServiceModel()
                .setAuthorId(userService.fetUserIdByUsername("testuser"))
                .setContent("Test comment"));

        mockMvc.perform(post("/comments/{commentId}/delete-comment", comment.getId())
                        .param("eventId", String.valueOf("")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments/chat-room"));

        assertThat(commentService.getAllCommentsWithoutEvent()).isEmpty();
    }
}
