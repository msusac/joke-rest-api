package hr.tvz.java.web.susac.joke.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.dto.CommentDTO;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import hr.tvz.java.web.susac.joke.service.CommentService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentControllerTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    public void getAllByJoke() throws Exception{
        this.mockMvc.perform(
                get("/api/joke/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @Order(2)
    public void getAllByUser() throws Exception{
        this.mockMvc.perform(
                get("/api/user/userone/comments")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(3)
    public void save() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setDescription("Hello world!");

        this.mockMvc.perform(
                post("/api/joke/2/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commentDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());

        List<CommentDTO> commentDTOList = commentService.findAllByJoke(2);

        assertEquals(1, commentDTOList.size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(4)
    public void save_ReplyTo() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setParentId(2);
        commentDTO.setDescription("Hello world!");

        this.mockMvc.perform(
                post("/api/joke/1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commentDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());

        List<CommentDTO> commentDTOList = commentService.findAllByJoke(1);

        assertEquals(4, commentDTOList.size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(5)
    public void save_NotFound() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setDescription("Hello world!");

        this.mockMvc.perform(
                post("/api/joke/99/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commentDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(6)
    public void save_FailedValidation() throws Exception {
        CommentDTO commentDTO = new CommentDTO();

        this.mockMvc.perform(
                post("/api/joke/2/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commentDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }
}
