package hr.tvz.java.web.susac.joke.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.dto.user.LoginDTO;
import hr.tvz.java.web.susac.joke.dto.user.RegisterDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@MockBean(SchedulerConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    public void getOneByUsername() throws Exception{
        this.mockMvc.perform(
                get("/api/user/admin")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getOneByUsername_NotFound() throws Exception{
        this.mockMvc.perform(
                get("/api/user/faileduser")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    public void getAllJokesByUsername() throws Exception{
        this.mockMvc.perform(
                get("/api/user/admin/joke")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @Order(4)
    public void getAllJokesByUsername_NotFound() throws Exception{
        this.mockMvc.perform(
                get("/api/user/faileduser/joke")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    public void login() throws Exception{
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("admin");
        loginDTO.setPassword("admin");

        this.mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    public void login_FailedValidation() throws Exception{
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("admin");

        this.mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @Order(7)
    public void login_Unauthorized() throws Exception{
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("userone");
        loginDTO.setPassword("wrongPassword");

        this.mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(8)
    public void login_NotEnabled() throws Exception{
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("usertwo");
        loginDTO.setPassword("usertwo");

        this.mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(9)
    public void register() throws Exception{
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("junituser");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("junit@user.com");

        this.mockMvc.perform(
                post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());
    }

    @Test
    @Order(10)
    public void register_FailedValidation() throws Exception{
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("junituser");

        this.mockMvc.perform(
                post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @Order(11)
    public void register_UsernameAlreadyTaken() throws Exception{
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("admin");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("junit@user.com");

        this.mockMvc.perform(
                post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @Order(12)
    public void register_EmailAlreadyTaken() throws Exception{
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("junituser");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("admin@admin.com");

        this.mockMvc.perform(
                post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @Order(13)
    public void register_PasswordsNotMatch() throws Exception{
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("junituser");
        registerDTO.setPassword("");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("junit@user.com");

        this.mockMvc.perform(
                post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @Order(14)
    public void accountVerification() throws Exception{
        this.mockMvc.perform(
                get("/api/user/accountVerification/testtwo")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("usertwo");
        loginDTO.setPassword("usertwo");

        this.mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    @Order(15)
    public void accountVerification_Failed() throws Exception{
        this.mockMvc.perform(
                get("/api/user/accountVerification/failed")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotAcceptable());

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("faileduser");
        loginDTO.setPassword("usertwo");

        this.mockMvc.perform(
                post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized());
    }
}
