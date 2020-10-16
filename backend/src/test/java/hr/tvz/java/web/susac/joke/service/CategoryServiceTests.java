package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.search.CategorySearchDTO;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    @Order(1)
    public void findOneById(){
        CategoryDTO categoryDTO  = categoryService.findOneById(1);

        assertNotNull(categoryDTO);
        assertEquals("Chuck Norris", categoryDTO.getName());
        assertEquals(2, categoryDTO.getJokeCount());
    }

    @Test
    @Order(2)
    public void findOneByName(){
        CategoryDTO categoryDTO = categoryService.findOneByName("Chuck Norris");

        assertNotNull(categoryDTO);
        assertEquals("Chuck Norris", categoryDTO.getName());
        assertEquals(2, categoryDTO.getJokeCount());
    }

    @Test
    @Order(3)
    public void findAllNameAsc(){
        List<CategoryDTO> categoryDTOList = categoryService.findAllNameAsc();

        assertNotNull(categoryDTOList);
        assertEquals(2, categoryDTOList.size());
        assertEquals("Chuck Norris", categoryDTOList.get(0).getName());
    }

    @Test
    @Order(4)
    public void findAllByNameLikeAsc(){
        CategorySearchDTO categorySearchDTO = new CategorySearchDTO();
        categorySearchDTO.setName("Chuck");

        List<CategoryDTO> categoryDTOList = categoryService.findAllByNameLikeAsc(categorySearchDTO);

        assertNotNull(categoryDTOList);
        assertEquals(1, categoryDTOList.size());
        assertEquals("Chuck Norris", categoryDTOList.get(0).getName());
    }

    @Test
    @Order(5)
    public void save(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Hello world!");

        categoryService.save(categoryDTO);

        categoryDTO = categoryService.findOneByName("Hello world!");

        assertNotNull(categoryDTO);
        assertEquals("Hello world!", categoryDTO.getName());
    }

    @Test
    @Order(6)
    public void deleteById(){
        CategoryDTO category = categoryService.findOneById(1);

        categoryService.deleteById(category.getId());

        category = categoryService.findOneById(1);

        assertNull(category);
    }
}
