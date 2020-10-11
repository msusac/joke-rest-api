package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import hr.tvz.java.web.susac.joke.model.Category;
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
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Order(1)
    public void findOneById(){
        Category category = categoryRepository.findOneById(1);

        assertNotNull(category);
        assertEquals("Chuck Norris", category.getName());
    }

    @Test
    @Order(2)
    public void findOneByName(){
        Category category = categoryRepository.findOneByName("Chuck Norris");

        assertNotNull(category);
        assertEquals("Chuck Norris", category.getName());
    }

    @Test
    @Order(3)
    public void findAllNameAsc(){
        List<Category> categoryList = categoryRepository.findAllNameAsc();

        assertNotNull(categoryList);
        assertEquals(2, categoryList.size());
        assertEquals("Chuck Norris", categoryList.get(0).getName());
    }

    @Test
    @Order(4)
    public void save(){
        Category category = new Category();
        category.setName("Hello world!");

        categoryRepository.save(category);

        category = categoryRepository.findOneByName("Hello world!");

        assertNotNull(category);
        assertEquals("Hello world!", category.getName());
    }

    @Test
    @Order(5)
    public void deleteById(){
        Category category = categoryRepository.findOneByName("Chuck Norris");

        categoryRepository.deleteById(category.getId());

        category = categoryRepository.findOneByName("Chuck Norris");

        assertNull(category);
    }
}
