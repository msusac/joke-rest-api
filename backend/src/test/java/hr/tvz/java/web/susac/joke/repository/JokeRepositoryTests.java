package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Category;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JokeRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void findOneById(){
        Joke joke = jokeRepository.findOneById(1);

        assertNotNull(joke);
        assertEquals("Chuck Norris", joke.getCategory().getName());
    }

    @Test
    @Order(2)
    public void findAllDateDesc(){
        List<Joke> jokeList = jokeRepository.findAllDateDesc();

        assertNotNull(jokeList);
        assertEquals(5, jokeList.size());
        assertEquals("Programming", jokeList.get(0).getCategory().getName());
    }

    @Test
    @Order(3)
    public void findAllByCategoryDateDesc(){
        List<Joke> jokeList = jokeRepository.findAllByCategoryDateDesc("Chuck Norris");

        assertNotNull(jokeList);
        assertEquals(2, jokeList.size());
        assertEquals("Chuck Norris", jokeList.get(0).getCategory().getName());
    }

    @Test
    @Order(4)
    public void findAllByCategoryLikeDateDesc(){
        List<Joke> jokeList = jokeRepository.findAllByCategoryLikeDateDesc("Pro");

        assertNotNull(jokeList);
        assertEquals(3, jokeList.size());
        assertEquals("Programming", jokeList.get(0).getCategory().getName());
    }

    @Test
    @Order(5)
    public void save(){
        Category category = categoryRepository.findOneByName("Programming");
        User user = userRepository.findOneByUsername("userone").get();

        Joke joke = new Joke();
        joke.setDescription("C++");
        joke.setCategory(category);
        joke.setUser(user);

        jokeRepository.save(joke);

        List<Joke> jokeList = jokeRepository.findAllByCategoryDateDesc("Programming");

        assertNotNull(jokeList);
        assertEquals(4, jokeList.size());
        assertEquals("C++", jokeList.get(0).getDescription());
    }

    @Test
    @Order(6)
    public void deleteById(){
        Joke joke = jokeRepository.findOneById(4);

        jokeRepository.deleteById(joke.getId());

        List<Joke> jokeList = jokeRepository.findAllByCategoryDateDesc("Programming");

        joke = jokeRepository.findOneById(4);

        assertNull(joke);
        assertNotNull(jokeList);
        assertEquals(2, jokeList.size());
    }


}
