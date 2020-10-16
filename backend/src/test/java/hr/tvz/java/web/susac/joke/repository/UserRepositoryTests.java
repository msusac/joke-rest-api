package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import hr.tvz.java.web.susac.joke.model.Authority;
import hr.tvz.java.web.susac.joke.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public class UserRepositoryTests {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void findOneByUsername(){
        User user = userRepository.findOneByUsername("admin").get();

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    @Test
    @Order(2)
    public void findOneByEmail(){
        User user = userRepository.findOneByEmail("admin@admin.com");

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    @Test
    @Order(3)
    public void findOneByEmailAndEnabledTrue(){
        User user = userRepository.findOneByUsernameAndEnabledTrue("admin");

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    @Test
    @Order(4)
    public void findAllByUsernameAndEmail(){
        List<User> userList = userRepository.findAllByUsernameAndEmail("admin", "admin");

        assertNotNull(userList);
        assertEquals(1, userList.size());
    }

    @Test
    @Order(5)
    public void save(){
        User user = new User();
        user.setUsername("junituser");
        user.setPassword("junituser");
        user.setEmail("junituser@junit.com");

        Authority authority = authorityRepository.findOneByName("ROLE_USER");
        user.getAuthoritySet().add(authority);

        userRepository.save(user);

        user = userRepository.findOneByUsername("junituser").get();

        assertNotNull(user);
        assertEquals("junituser", user.getUsername());
    }

    @Test
    @Order(6)
    public void deleteById(){
        Optional<User> user = userRepository.findOneByUsername("admin");

        userRepository.deleteById(user.get().getId());

        user = userRepository.findOneByUsername("admin");

        assertEquals(false, user.isPresent());
    }
}
