package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO findOneById(Integer id);
    CategoryDTO findOneByName(String name);

    List<CategoryDTO> findAllNameAsc();

    CategoryDTO save(CategoryDTO categoryDTO);

    void deleteById(Integer id);
}
