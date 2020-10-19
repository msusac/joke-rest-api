package hr.tvz.java.web.susac.joke.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.dto.rating.RatingDTO;
import hr.tvz.java.web.susac.joke.enums.RatingTypeEnum;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import hr.tvz.java.web.susac.joke.service.RatingService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingControllerTests {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(1)
    public void getOneByJokeAndUser() throws Exception{
        this.mockMvc.perform(
                get("/api/joke/1/rating")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(2)
    public void getOneByJokeAndUser_NotFound() throws Exception{
        this.mockMvc.perform(
                get("/api/joke/4/rating")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    public void getAllInOneByJoke() throws Exception {
        this.mockMvc.perform(
                get("/api/joke/1/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(4)
    public void save() throws Exception {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setRatingType(RatingTypeEnum.FUNNY);

        this.mockMvc.perform(
                post("/api/joke/4/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        ratingDTO = ratingService.findOneByJokeAndUser(4, "admin");

        assertNotNull(ratingDTO);
        assertEquals("admin", ratingDTO.getUser());
        assertEquals(RatingTypeEnum.FUNNY, ratingDTO.getRatingType());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(5)
    public void save_FailedValidation() throws Exception {
        RatingDTO ratingDTO = new RatingDTO();

        this.mockMvc.perform(
                post("/api/joke/4/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(6)
    public void save_NotFound() throws Exception {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setRatingType(RatingTypeEnum.FUNNY);

        this.mockMvc.perform(
                post("/api/joke/99/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(7)
    public void save_Existing() throws Exception {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setRatingType(RatingTypeEnum.WOW);

        this.mockMvc.perform(
                post("/api/joke/1/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        ratingDTO = ratingService.findOneByJokeAndUser(1, "admin");

        assertNotNull(ratingDTO);
        assertEquals("admin", ratingDTO.getUser());
        assertEquals(RatingTypeEnum.WOW, ratingDTO.getRatingType());
    }
}
