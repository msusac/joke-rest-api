package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class CategoryServiceTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    @Order(1)
    public void findOneById(){
        CategoryDTO categoryDTO = categoryService.findOneById(LONG_ID_ONE);
        assertNotNull(categoryDTO);
        assertEquals(STRING_CHUCK_NORRIS, categoryDTO.getTitle());
    }

    @Test
    @Order(2)
    public void findAll(){
        List<CategoryDTO> categoryDTOList = categoryService.findAll();
        assertEquals(4, categoryDTOList.size());
        assertEquals(STRING_CHUCK_NORRIS, categoryDTOList.get(0).getTitle());
    }

    @Test
    @Order(3)
    public void findAllByParam(){
        List<CategoryDTO> categoryDTOList = categoryService.findAllByParam(createCategorySearchDTO());
        assertEquals(1, categoryDTOList.size());
        assertEquals(STRING_PROGRAMMING, categoryDTOList.get(0).getTitle());
    }
}
