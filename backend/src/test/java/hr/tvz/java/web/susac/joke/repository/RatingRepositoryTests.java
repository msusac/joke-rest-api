package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.enums.RatingTypeEnum;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.model.Rating;
import hr.tvz.java.web.susac.joke.model.User;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingRepositoryTests {

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void findAllByJokeId(){
        List<Rating> ratingList = ratingRepository.findAllByJokeId(1);

        assertNotNull(ratingList);
        assertEquals(5, ratingList.size());
    }

    @Test
    @Order(2)
    public void findAllLikedByJokeId(){
        List<Rating> ratingList = ratingRepository.findAllLikedByJokeId(1);

        assertNotNull(ratingList);
        assertEquals(1, ratingList.size());
    }

    @Test
    @Order(3)
    public void findAllFunnyByJokeId(){
        List<Rating> ratingList = ratingRepository.findAllFunnyByJokeId(1);

        assertNotNull(ratingList);
        assertEquals(2, ratingList.size());
    }

    @Test
    @Order(4)
    public void findAllWowByJokeId(){
        List<Rating> ratingList = ratingRepository.findAllWowByJokeId(1);

        assertNotNull(ratingList);
        assertEquals(1, ratingList.size());
    }

    @Test
    @Order(5)
    public void findAllNotFunnyByJokeId(){
        List<Rating> ratingList = ratingRepository.findAllNotFunnyByJokeId(1);

        assertNotNull(ratingList);
        assertEquals(1, ratingList.size());
    }

    @Test
    @Order(6)
    public void findOneByJokeIdAndUsername(){
        Rating rating = ratingRepository.findOneByJokeIdAndUsername(1, "admin");

        assertNotNull(rating);
        assertEquals(RatingTypeEnum.FUNNY, rating.getType());
        assertEquals("admin", rating.getUser().getUsername());
    }

    @Test
    @Order(7)
    public void save(){
        Joke joke = jokeRepository.findOneById(1);
        User user = userRepository.findOneByUsername("admin").get();

        Rating rating = ratingRepository.findOneByJokeIdAndUsername(1, "admin");
        rating.setType(RatingTypeEnum.WOW);
        rating.setJoke(joke);
        rating.setUser(user);

        ratingRepository.save(rating);

        rating = ratingRepository.findOneByJokeIdAndUsername(1, "admin");

        assertNotNull(rating);
        assertEquals(RatingTypeEnum.WOW, rating.getType());
        assertEquals("admin", rating.getUser().getUsername());
    }
}
