package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.dto.CommentDTO;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTests {

    @Autowired
    private CommentService commentService;

    @Test
    @Order(1)
    public void findOneById(){
        CommentDTO commentDTO = commentService.findOneById(1);

        assertNotNull(commentDTO);
    }

    @Test
    @Order(2)
    public void findAllByJoke(){
        List<CommentDTO> commentDTOList = commentService.findAllByJoke(1);

        assertNotNull(commentDTOList);
        assertEquals(3, commentDTOList.size());
    }

    @Test
    @Order(3)
    public void findAllByUser(){
        List<CommentDTO> commentDTOList = commentService.findAllByUser(2);

        assertNotNull(commentDTOList);
        assertEquals(2, commentDTOList.size());
    }

    @Test
    @Order(4)
    public void save(){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setUser("admin");
        commentDTO.setDescription("Test Comment!");
        commentDTO.setJokeId(2);

        commentService.save(commentDTO);

        List<CommentDTO> commentDTOList = commentService.findAllByJoke(2);

        assertNotNull(commentDTOList);
        assertEquals(1, commentDTOList.size());
    }

    @Test
    @Order(5)
    public void save_Reply(){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setUser("usersix");
        commentDTO.setDescription("Test Comment!");
        commentDTO.setJokeId(1);
        commentDTO.setParentId(1);

        commentService.save(commentDTO);

        List<CommentDTO> commentDTOList = commentService.findAllByJoke(1);

        assertNotNull(commentDTOList);
        assertEquals(4, commentDTOList.size());
    }

    @Test
    @Order(6)
    public void deleteById() {
        CommentDTO commentDTO = commentService.findOneById(1);

        commentService.deleteById(commentDTO.getId());

        commentDTO = commentService.findOneById(1);

        assertNull(commentDTO);
    }
}
