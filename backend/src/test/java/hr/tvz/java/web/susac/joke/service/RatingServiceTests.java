package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.dto.rating.RatingDTO;
import hr.tvz.java.web.susac.joke.dto.rating.RatingDisplayDTO;
import hr.tvz.java.web.susac.joke.enums.RatingTypeEnum;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingServiceTests {

    @Autowired
    private RatingService ratingService;

    @Test
    @Order(1)
    public void findOneByJokeAndUser(){
        RatingDTO ratingDTO = ratingService.findOneByJokeAndUser(1, "usertwo");

        assertNotNull(ratingDTO);
        assertEquals(RatingTypeEnum.LIKED, ratingDTO.getRatingTypeEnum());
        assertEquals(1, ratingDTO.getJokeId());
        assertEquals("usertwo", ratingDTO.getUser());
    }

    @Test
    @Order(2)
    public void save(){
        RatingDTO ratingDTO = ratingService.findOneByJokeAndUser(1, "usertwo");;
        ratingDTO.setRatingTypeEnum(RatingTypeEnum.WOW);

        ratingService.save(ratingDTO);

        ratingDTO = ratingService.findOneByJokeAndUser(1, "usertwo");

        assertNotNull(ratingDTO);
        assertEquals(RatingTypeEnum.WOW, ratingDTO.getRatingTypeEnum());
        assertEquals(1, ratingDTO.getJokeId());
        assertEquals("usertwo", ratingDTO.getUser());
    }
}
