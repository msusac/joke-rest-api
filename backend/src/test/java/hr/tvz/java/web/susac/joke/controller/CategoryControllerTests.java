package hr.tvz.java.web.susac.joke.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.service.CategoryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerTests {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void getAll() throws Exception{
        this.mockMvc.perform(
                get("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(2)
    public void getOneById() throws Exception{
        this.mockMvc.perform(
                get("/api/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(3)
    public void getOneById_NotFound() throws Exception{
        this.mockMvc.perform(
                get("/api/category/11")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    public void getOneByIdDetails() throws Exception{
        this.mockMvc.perform(
                get("/api/category/1/details")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(5)
    public void save() throws Exception{
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("My Cake");

        this.mockMvc.perform(
                post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());

        categoryDTO = categoryService.findOneByName("My Cake");

        assertNotNull(categoryDTO);
        assertEquals("My Cake", categoryDTO.getName());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(6)
    public void save_FailedValidation() throws Exception{
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("My Cake 342");

        this.mockMvc.perform(
                post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(7)
    public void save_ExistingCategory() throws Exception{
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Programming");

        this.mockMvc.perform(
                post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(8)
    public void update() throws Exception{
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Chucky Norris");

        this.mockMvc.perform(
                put("/api/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        categoryDTO = categoryService.findOneByName("Chucky Norris");

        assertNotNull(categoryDTO);
        assertEquals(1, categoryDTO.getId());
        assertEquals("Chucky Norris", categoryDTO.getName());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(9)
    public void update_FailedValidation() throws Exception{
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Chucky Norris 231");

        this.mockMvc.perform(
                put("/api/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(10)
    public void update_ExistingCategory() throws Exception{
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Programming");

        this.mockMvc.perform(
                put("/api/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(11)
    public void update_NotFound() throws Exception{
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Chucky Norris");

        this.mockMvc.perform(
                put("/api/category/9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Order(12)
    public void deleteById() throws Exception{
        this.mockMvc.perform(
                delete("/api/category/1")
        )
                .andExpect(status().isOk());

        this.mockMvc.perform(
                get("/api/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }
}
