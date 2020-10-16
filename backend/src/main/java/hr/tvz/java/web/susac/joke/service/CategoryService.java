package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.search.CategorySearchDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO findOneById(Integer id);
    CategoryDTO findOneByName(String name);

    List<CategoryDTO> findAllNameAsc();
    List<CategoryDTO> findAllByNameLikeAsc(CategorySearchDTO categorySearchDTO);

    CategoryDTO save(CategoryDTO categoryDTO);

    void deleteById(Integer id);
}
