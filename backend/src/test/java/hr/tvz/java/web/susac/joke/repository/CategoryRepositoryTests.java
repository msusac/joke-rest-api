package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.model.Category;
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
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Order(1)
    public void findOneById(){
        Category category = categoryRepository.findOneById(LONG_ID_ONE).orElse(null);
        assertNotNull(category);
        assertEquals(STRING_CHUCK_NORRIS, category.getTitle());
    }

    @Test
    @Order(2)
    public void findOneByTitle(){
        Category category = categoryRepository.findOneByTitle(STRING_CHUCK_NORRIS).orElse(null);
        assertNotNull(category);
        assertEquals(STRING_CHUCK_NORRIS, category.getTitle());
    }

    @Test
    @Order(3)
    public void findAllTitleAsc(){
        List<Category> categoryList = categoryRepository.findAllTitleAsc();
        assertEquals(4, categoryList.size());
        assertEquals(STRING_CHUCK_NORRIS, categoryList.get(0).getTitle());
    }

    @Test
    @Order(4)
    public void findAllByTitleLike() {
        List<Category> categoryList = categoryRepository.findAllByParam(STRING_PRO);
        assertEquals(1, categoryList.size());
        assertEquals(STRING_PROGRAMMING, categoryList.get(0).getTitle());
    }

    @Test
    @Order(5)
    public void save(){
        categoryRepository.save(createCategory());

        Category category = categoryRepository.findOneByTitle(STRING_HELLO_WORLD).orElse(null);
        assertNotNull(category);
        assertEquals(STRING_HELLO_WORLD, category.getTitle());
    }

    @Test
    @Order(6)
    public void deleteById(){
        categoryRepository.deleteById(LONG_ID_ONE);

        Category category = categoryRepository.findOneById(LONG_ID_ONE).orElse(null);
        assertNull(category);
    }
}
