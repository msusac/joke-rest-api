package hr.tvz.java.web.susac.joke.util.converter.impl;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.model.Category;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.repository.CategoryRepository;
import hr.tvz.java.web.susac.joke.repository.JokeRepository;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
@Qualifier("CategoryConverter")
public class CategoryConverterImpl implements ConverterUtil<Category, CategoryDTO> {

    private final CategoryRepository categoryRepository;
    private final JokeRepository jokeRepository;

    private final ModelMapper mapper;

    @Override
    public CategoryDTO convertToDTO(Category entity) {
        CategoryDTO categoryDTO = mapper.map(entity, CategoryDTO.class);
        categoryDTO.setDateCreated(entity.getDateTimeCreated().toLocalDate());

        List<Joke> jokeList = jokeRepository.findAllByCategoryDateDesc(entity.getName());
        categoryDTO.setJokeCount(jokeList.size());

        if(!Objects.isNull(entity.getDateTimeUpdated())) {
            categoryDTO.setDateUpdated(entity.getDateTimeUpdated().toLocalDate());
        }

        return categoryDTO;
    }

    @Override
    public Category convertToEntity(CategoryDTO dto) {
        Category category = mapper.map(dto, Category.class);

        if(!Objects.isNull(category.getId())){
            Category existingCategory = categoryRepository.findOneById(category.getId());
            category.setJokeList(existingCategory.getJokeList());
            category.setDateTimeCreated(existingCategory.getDateTimeCreated());
            category.setDateTimeUpdated(LocalDateTime.now());
        }

        return category;
    }
}
