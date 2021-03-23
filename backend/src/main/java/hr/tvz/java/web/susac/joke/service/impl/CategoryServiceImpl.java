package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.dto.JokeSearchDTO;
import hr.tvz.java.web.susac.joke.model.Category;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.repository.CategoryRepository;
import hr.tvz.java.web.susac.joke.service.CategoryService;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ConverterUtil<Category, CategoryDTO> converterUtil;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(converterUtil::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> findAllByParam(CategorySearchDTO searchDTO) {
        List<Category> categoryList = categoryRepository.findAllByParam(searchDTO.getTitle());

        return categoryList.stream().map(converterUtil::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findOneById(Long id) {
        Category category = categoryRepository.findOneById(id).orElse(null);

        return category != null ? converterUtil.convertToDTO(category): null;
    }
}
