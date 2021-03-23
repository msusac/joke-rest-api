package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Joke;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import java.util.List;

import static hr.tvz.java.web.susac.joke.UtilStatic.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class JokeRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JokeRepository jokeRepository;

    @Test
    @Order(1)
    public void findOneById(){
        Joke joke = jokeRepository.findOneById(LONG_ID_ONE).orElse(null);
        assertNotNull(joke);
        assertEquals(STRING_CHUCK_NORRIS, joke.getCategory().getTitle());
    }

    @Test
    @Order(2)
    public void findAllByCategoryId(){
        List<Joke> jokeList = jokeRepository.findAllByCategoryId(LONG_ID_ONE);
        assertEquals(3, jokeList.size());
    }

    @Test
    @Order(3)
    public void findAllByParams(){
        List<Joke> jokeList = jokeRepository.findAllByParam(STRING_PRO);
        assertEquals(2, jokeList.size());
    }

    @Test
    @Order(4)
    public void findAllByParamsNewest(){
        List<Joke> jokeList = jokeRepository.findAllByParamNewest(STRING_PRO);
        assertEquals(2, jokeList.size());
    }

    @Test
    @Order(5)
    public void findAllByParamsOldest(){
        List<Joke> jokeList = jokeRepository.findAllByParamOldest(STRING_PRO);
        assertEquals(2, jokeList.size());
    }

    @Test
    @Order(5)
    public void findAllByRandomFive(){
        List<Joke> jokeList = jokeRepository.findAllByRandomFive();
        assertEquals(5, jokeList.size());
    }

    @Test
    @Order(6)
    public void save(){
        jokeRepository.save(createJoke(categoryRepository));

        List<Joke> jokeList = jokeRepository.findAllByParam(STRING_PROGRAMMING);
        assertEquals(3, jokeList.size());
    }

    @Test
    @Order(7)
    public void deleteById(){
        jokeRepository.deleteById(LONG_ID_ONE);

        Joke joke = jokeRepository.findOneById(LONG_ID_ONE).orElse(null);
        assertNull(joke);
    }
}
