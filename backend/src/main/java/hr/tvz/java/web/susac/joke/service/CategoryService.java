package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();

    List<CategoryDTO> findAllByParam(CategorySearchDTO searchDTO);

    CategoryDTO findOneById(Long id);
}
