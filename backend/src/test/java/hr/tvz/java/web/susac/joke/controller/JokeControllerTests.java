package hr.tvz.java.web.susac.joke.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.JokeSearchDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static hr.tvz.java.web.susac.joke.UtilStatic.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class JokeControllerTests {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void getAllByParam() throws Exception {
        JokeSearchDTO searchDTO = createJokeSearchDTO();

        this.mockMvc.perform(
                post(URL_API_JOKE + "/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(searchDTO))
                    .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(2)
    public void getAllByRandom() throws Exception {
        this.mockMvc.perform(
                get(URL_API_JOKE + "/random")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @Order(3)
    public void getOneById() throws Exception {
        this.mockMvc.perform(
                get(URL_API_JOKE + "/" + LONG_ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(4)
    public void getOneById_BadRequest_NotFound() throws Exception {
        this.mockMvc.perform(
                get(URL_API_JOKE + "/" + LONG_ID_ELEVEN)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    public void save_existingCategory() throws Exception {
        JokeDTO jokeDTO = createJokeDTO_withExistingCategory();

        this.mockMvc.perform(
                post(URL_API_JOKE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(6)
    public void save_newCategory() throws Exception {
        JokeDTO jokeDTO = createJokeDTO_withExistingCategory();

        this.mockMvc.perform(
                post(URL_API_JOKE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(7)
    public void save_BadRequest_FailedValidation() throws Exception {
        JokeDTO jokeDTO = createJokeDTO_incomplete();

        this.mockMvc.perform(
                post(URL_API_JOKE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    public void updateById_existingCategory() throws Exception {
        JokeDTO jokeDTO = createJokeDTO_withExistingCategory();

        this.mockMvc.perform(
                put(URL_API_JOKE + "/" + LONG_ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(9)
    public void updateById_newCategory() throws Exception {
        JokeDTO jokeDTO = createJokeDTO_withNewCategory();

        this.mockMvc.perform(
                put(URL_API_JOKE + "/" + LONG_ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(10)
    public void updateById_BadRequest_FailedValidation() throws Exception {
        JokeDTO jokeDTO = createJokeDTO_incomplete();

        this.mockMvc.perform(
                put(URL_API_JOKE + "/" + LONG_ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(11)
    public void updateById_BadRequest_NotFound() throws Exception {
        JokeDTO jokeDTO = createJokeDTO_withNewCategory();

        this.mockMvc.perform(
                put(URL_API_JOKE + "/" + LONG_ID_ELEVEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(12)
    public void deleteById() throws Exception {
        this.mockMvc.perform(
                delete(URL_API_JOKE + "/" + LONG_ID_ONE)
        )
                .andExpect(status().isOk());
    }

    @Test
    @Order(13)
    public void deleteById_BadRequest_NotFound() throws Exception {
        this.mockMvc.perform(
                delete(URL_API_JOKE + "/" + LONG_ID_ELEVEN)
        )
                .andExpect(status().isBadRequest());
    }
}
