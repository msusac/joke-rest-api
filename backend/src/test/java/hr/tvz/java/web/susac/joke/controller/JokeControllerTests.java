package hr.tvz.java.web.susac.joke.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.service.CategoryService;
import hr.tvz.java.web.susac.joke.service.JokeService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@MockBean(SchedulerConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JokeControllerTests {

    @Autowired
    private JokeService jokeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    public void getAll() throws Exception{
        this.mockMvc.perform(
                get("/api/joke")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @Order(2)
    public void getOneById() throws Exception{
        this.mockMvc.perform(
                get("/api/joke/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(3)
    public void getOneById_NotFound() throws Exception{
        this.mockMvc.perform(
                get("/api/joke/11")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    public void getAllByParam() throws Exception{
        CategorySearchDTO categorySearchDTO = new CategorySearchDTO();
        categorySearchDTO.setName("Prog");

        this.mockMvc.perform(
                post("/api/joke/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categorySearchDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(5)
    public void save_NewCategory() throws Exception{
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setCategory("School");
        jokeDTO.setDescription("I got F++!");

        this.mockMvc.perform(
                post("/api/joke")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());

        List<JokeDTO> jokeDTOList = jokeService.findAllByCategory("School");

        assertEquals(1, jokeDTOList.size());
    }

    @Test
    @WithMockUser(username = "userone", authorities = "ROLE_USER")
    @Order(6)
    public void save_ExistingCategory() throws Exception{
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setCategory("Programming");
        jokeDTO.setDescription("I got C++!");

        this.mockMvc.perform(
                post("/api/joke")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());

        List<JokeDTO> jokeDTOList = jokeService.findAllByCategory("Programming");

        assertEquals(4, jokeDTOList.size());
    }

    @Test
    @WithMockUser(username = "userone", authorities = "ROLE_USER")
    @Order(7)
    public void save_FailedValidation() throws Exception{
        JokeDTO jokeDTO = new JokeDTO();

        this.mockMvc.perform(
                post("/api/joke")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(8)
    public void update_NewCategory() throws Exception{
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setCategory("School");
        jokeDTO.setDescription("I got D++!");;

        this.mockMvc.perform(
                put("/api/joke/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        jokeDTO = jokeService.findOneById(3);

        assertNotNull(jokeDTO);
        assertEquals("School", jokeDTO.getCategory());
        assertEquals("I got D++!", jokeDTO.getDescription());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(9)
    public void update_ExistingCategory() throws Exception{
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setCategory("Programming");
        jokeDTO.setDescription("I got C++!");;

        this.mockMvc.perform(
                put("/api/joke/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        jokeDTO = jokeService.findOneById(3);

        assertNotNull(jokeDTO);
        assertEquals("Programming", jokeDTO.getCategory());
        assertEquals("I got C++!", jokeDTO.getDescription());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(10)
    public void update_FailedValidation() throws Exception{
        JokeDTO jokeDTO = new JokeDTO();

        this.mockMvc.perform(
                put("/api/joke/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(11)
    public void update_NotFound() throws Exception{
        JokeDTO jokeDTO = new JokeDTO();

        this.mockMvc.perform(
                put("/api/joke/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jokeDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(12)
    public void deleteById() throws Exception{
        this.mockMvc.perform(
                delete("/api/joke/1")
        )
                .andExpect(status().isOk());

        this.mockMvc.perform(
                get("/api/joke/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }
}
