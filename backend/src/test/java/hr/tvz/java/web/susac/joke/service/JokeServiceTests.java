package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
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
public class JokeServiceTests {

    @Autowired
    private JokeService jokeService;

    @Test
    @Order(1)
    public void findOneById() {
        JokeDTO jokeDTO = jokeService.findOneById(LONG_ID_ONE);
        assertNotNull(jokeDTO);
        assertEquals(STRING_CHUCK_NORRIS, jokeDTO.getCategoryTitle());
    }

    @Test
    @Order(2)
    public void findAllByCategory(){
        List<JokeDTO> jokeDTOList = jokeService.findAllByCategory(LONG_ID_ONE);
        assertEquals(3, jokeDTOList.size());
    }

    @Test
    @Order(3)
    public void findAllByParam(){
        List<JokeDTO> jokeDTOList = jokeService.findAllByParam(createJokeSearchDTO());
        assertEquals(2, jokeDTOList.size());
    }

    @Test
    @Order(4)
    public void findAllByParam_withDate(){
        List<JokeDTO> jokeDTOList = jokeService.findAllByParam(createSearchDTO_withDate());
        assertEquals(2, jokeDTOList.size());
    }

    @Test
    @Order(5)
    public void findAllByRandom(){
        List<JokeDTO>jokeDTOList = jokeService.findAllByRandom();
        assertEquals(5, jokeDTOList.size());
    }

    @Test
    @Order(5)
    public void save_withExistingCategory(){
        jokeService.save(createJokeDTO_withExistingCategory());

        List<JokeDTO> jokeDTOList = jokeService.findAllByParam(createJokeSearchDTO());
        assertEquals(3, jokeDTOList.size());
    }

    @Test
    @Order(6)
    public void save_withNewCategory(){
        jokeService.save(createJokeDTO_withNewCategory());

        List<JokeDTO> jokeDTOList = jokeService.findAllByParam(createSearchDTO_withNameNewJoke());
        assertEquals(1, jokeDTOList.size());
    }

    @Test
    @Order(7)
    public void deleteById(){
        jokeService.deleteById(LONG_ID_ONE);

        JokeDTO jokeDTO = jokeService.findOneById(LONG_ID_ONE);
        assertNull(jokeDTO);
    }
}
