package hr.tvz.java.web.susac.joke.util.converter.impl;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.model.Category;
import hr.tvz.java.web.susac.joke.repository.CategoryRepository;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
@Qualifier("CategoryConverter")
public class CategoryConverterImpl implements ConverterUtil<Category, CategoryDTO> {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO convertToDTO(Category entity) {
        CategoryDTO categoryDTO = modelMapper.map(entity, CategoryDTO.class);
        categoryDTO.setJokeCount(entity.getJokeList().size());

        return categoryDTO;
    }

    @Override
    public Category convertToEntity(CategoryDTO dto) {
        Category category = modelMapper.map(dto, Category.class);

        if (!Objects.isNull(category.getId())){
            Category oldCategory = categoryRepository.findOneById(category.getId()).orElse(null);
            category.setJokeList(oldCategory.getJokeList());
            category.setDateTimeCreated(oldCategory.getDateTimeCreated());
        }

        return category;
    }
}
