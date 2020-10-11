package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.model.Category;
import hr.tvz.java.web.susac.joke.repository.CategoryRepository;
import hr.tvz.java.web.susac.joke.service.CategoryService;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ConverterUtil<Category, CategoryDTO> converter;

    @Override
    public CategoryDTO findOneById(Integer id) {
        Category category = categoryRepository.findOneById(id);

        if(Objects.isNull(category)) return null;

        return converter.convertToDTO(category);
    }

    @Override
    public CategoryDTO findOneByName(String name) {
        Category category = categoryRepository.findOneByName(name);

        if(Objects.isNull(category)) return null;

        return converter.convertToDTO(category);
    }

    @Override
    public List<CategoryDTO> findAllNameAsc() {
        List<Category> categoryList = categoryRepository.findAllNameAsc();

        return categoryList.stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = converter.convertToEntity(categoryDTO);

        categoryRepository.save(category);

        return converter.convertToDTO(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
