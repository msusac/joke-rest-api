package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import hr.tvz.java.web.susac.joke.model.Comment;
import hr.tvz.java.web.susac.joke.model.Joke;
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
public class CommentRepositoryTests {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void findOneById(){
        Comment comment = commentRepository.findOneById(1);

        assertNotNull(comment);
    }

    @Test
    @Order(2)
    public void findOneByIdAndJokeId(){
        Comment comment = commentRepository.findOneByIdAndJoke_Id(1, 1);

        assertNotNull(comment);
    }

    @Test
    @Order(3)
    public void findAllNewestByJokeId(){
        Joke joke = jokeRepository.findOneById(1);

        List<Comment> commentList = commentRepository.findAllNewestByJokeId(joke.getId());

        assertNotNull(commentList);
        assertEquals(3, commentList.size());
    }

    @Test
    @Order(4)
    public void findAllNewestByUserId(){
        User user = userRepository.findOneByUsername("admin").get();

        List<Comment> commentList = commentRepository.findAllNewestByUserId(user.getId());

        assertNotNull(commentList);
        assertEquals(1, commentList.size());
    }

    @Test
    @Order(5)
    public void save(){
        Joke joke = jokeRepository.findOneById(3);
        User user = userRepository.findOneByUsername("admin").get();

        Comment comment = new Comment();
        comment.setDescription("Test Comment!");
        comment.setJoke(joke);
        comment.setUser(user);

        commentRepository.save(comment);

        List<Comment> commentList = commentRepository.findAllNewestByJokeId(joke.getId());

        assertNotNull(commentList);
        assertEquals(1, commentList.size());
    }

    @Test
    @Order(6)
    public void save_reply() {
        Joke joke = jokeRepository.findOneById(3);
        User user = userRepository.findOneByUsername("admin").get();

        Comment comment = new Comment();
        comment.setDescription("Test Comment!");
        comment.setJoke(joke);
        comment.setUser(user);

        commentRepository.save(comment);

        List<Comment> commentList = commentRepository.findAllNewestByJokeId(joke.getId());

        assertNotNull(commentList);
        assertEquals(1, commentList.size());
    }

    @Test
    @Order(7)
    public void deleteById(){
        Comment comment = commentRepository.findOneById(1);

        commentRepository.deleteById(comment.getId());

        List<Comment> commentList = commentRepository.findAllNewestByJokeId(1);

        assertEquals(0, commentList.size());
    }
}
