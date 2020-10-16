package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.dto.search.UserSearchDTO;
import hr.tvz.java.web.susac.joke.dto.user.LoginDTO;
import hr.tvz.java.web.susac.joke.dto.user.RegisterDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import hr.tvz.java.web.susac.joke.model.User;
import hr.tvz.java.web.susac.joke.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void findOneByUsernameEnabled(){
        UserDTO userDTO = userService.findOneByUsernameEnabled("admin");

        assertNotNull(userDTO);
        assertEquals("admin", userDTO.getUsername());
        assertEquals("Administrator", userDTO.getAuthority());
        assertEquals(3, userDTO.getJokeCount());
    }

    @Test
    @Order(2)
    public void findAllByParam(){
        UserSearchDTO userSearchDTO = new UserSearchDTO();
        userSearchDTO.setUsername("admin");
        userSearchDTO.setEmail("admin");

        List<UserDTO> userDTOList = userService.findAllByParam(userSearchDTO);

        assertNotNull(userDTOList);
        assertEquals(1, userDTOList.size());
    }

    @Test
    @Order(3)
    public void isEnabled(){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("admin");
        loginDTO.setPassword("admin");

        assertEquals(true, userService.isEnabled(loginDTO));
    }

    @Test
    @Order(4)
    public void isEnabled_Failed(){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("usertwo");
        loginDTO.setPassword("usertwo");

        assertEquals(false, userService.isEnabled(loginDTO));
    }

    @Test
    @Order(5)
    public void isFreeEmail(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("junituser");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("junit@user.com");

        assertEquals(true, userService.isFreeEmail(registerDTO));
    }

    @Test
    @Order(6)
    public void isFreeEmail_Failed(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("junituser");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("admin@admin.com");

        assertEquals(false, userService.isFreeEmail(registerDTO));
    }

    @Test
    @Order(7)
    public void isFreeUsername(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("junituser");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("junit@user.com");

        assertEquals(true, userService.isFreeUsername(registerDTO));
    }

    @Test
    @Order(8)
    public void isFreeUsername_Failed(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("admin");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("junit@user.com");

        assertEquals(false, userService.isFreeUsername(registerDTO));
    }

    @Test
    @Order(9)
    public void doPasswordsMatch(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("admin");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("junit@user.com");

        assertEquals(true, userService.doPasswordsMatch(registerDTO));
    }

    @Test
    @Order(10)
    public void doPasswordsMatch_Failed(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("admin");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("");
        registerDTO.setEmail("junit@user.com");

        assertEquals(false, userService.doPasswordsMatch(registerDTO));
    }

    @Test
    @Order(11)
    public void register(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("junituser");
        registerDTO.setPassword("junituser");
        registerDTO.setRepeatPassword("junituser");
        registerDTO.setEmail("junit@user.com");

        userService.register(registerDTO);

        Optional<User> user = userRepository.findOneByUsername("junituser");

        assertEquals(true, user.isPresent());
        assertEquals("junituser", user.get().getUsername());
    }
}
