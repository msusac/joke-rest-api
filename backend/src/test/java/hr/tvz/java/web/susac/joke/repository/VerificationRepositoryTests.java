package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import hr.tvz.java.web.susac.joke.model.Authority;
import hr.tvz.java.web.susac.joke.model.User;
import hr.tvz.java.web.susac.joke.model.Verification;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerificationRepositoryTests {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Test
    @Order(1)
    public void findOneByToken(){
        Verification verification = verificationRepository.findOneByToken("testtwo");

        assertNotNull(verification);
        assertEquals("testtwo", verification.getToken());
    }

    @Test
    @Order(2)
    public void findOneByTokenNotExpired(){
        Verification verification = verificationRepository.findOneByTokenNotExpired("testtwo");

        assertNotNull(verification);
        assertEquals("testtwo", verification.getToken());
    }

    @Test
    @Order(3)
    public void findAllByDateExpiredDayLater(){
        List<Verification> verificationList = verificationRepository.findAllByDateExpiredDayLater();

        assertNotNull(verificationList);
        assertEquals(1, verificationList.size());
    }

    @Test
    @Order(4)
    public void save(){
        User user = new User();
        user.setUsername("junituser");
        user.setPassword("junituser");
        user.setEmail("junituser@junit.com");

        Authority authority = authorityRepository.findOneByName("ROLE_USER");
        user.getAuthoritySet().add(authority);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        while(!Objects.isNull(verificationRepository.findOneByTokenNotExpired(token))){
            token = UUID.randomUUID().toString();
        }

        Verification verification = new Verification();
        verification.setToken(token);
        verification.setUser(user);
        verification.setDateExpiry(LocalDate.now().plusDays(3));

        verificationRepository.save(verification);

        verification = verificationRepository.findOneByTokenNotExpired(token);

        assertNotNull(verification);
        assertEquals(token, verification.getToken());
        assertEquals("junituser", verification.getUser().getUsername());
    }

    @Test
    @Order(5)
    public void deleteById(){
        Verification verification = verificationRepository.findOneByToken("testtwo");

        verificationRepository.deleteById(verification.getId());

        verification = verificationRepository.findOneByToken("testtwo");

        assertNull(verification);
    }
}
