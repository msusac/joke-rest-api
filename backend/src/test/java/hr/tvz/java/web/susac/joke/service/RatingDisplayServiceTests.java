package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.dto.rating.RatingDisplayDTO;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingDisplayServiceTests {

    @Autowired
    private RatingDisplayService ratingDisplayService;

    @Test
    @Order(1)
    public void findOneByJokeId(){
        RatingDisplayDTO ratingDisplayDTO = ratingDisplayService.findAllInOneByJoke(1);

        assertNotNull(ratingDisplayDTO);
        assertEquals(2, ratingDisplayDTO.getFunnyCount());
        assertEquals(1, ratingDisplayDTO.getLikedCount());
        assertEquals(1, ratingDisplayDTO.getWowCount());
        assertEquals(1, ratingDisplayDTO.getNotFunnyCount());
    }
}
