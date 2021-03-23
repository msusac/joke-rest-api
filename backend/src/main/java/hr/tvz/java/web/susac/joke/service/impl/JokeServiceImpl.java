package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.JokeSearchDTO;
import hr.tvz.java.web.susac.joke.model.Category;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.repository.CategoryRepository;
import hr.tvz.java.web.susac.joke.repository.JokeRepository;
import hr.tvz.java.web.susac.joke.service.JokeService;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class JokeServiceImpl implements JokeService {

    private final CategoryRepository categoryRepository;
    private final JokeRepository jokeRepository;

    private final ConverterUtil<Joke, JokeDTO> converterUtil;

    @Override
    public JokeDTO findOneById(Long id) {
        Joke joke = jokeRepository.findOneById(id).orElse(null);

        return joke != null ? converterUtil.convertToDTO(joke): null;
    }

    @Override
    public List<JokeDTO> findAllByCategory(Long id) {
        List<Joke> jokeList = jokeRepository.findAllByCategoryId(id);

        return jokeList.stream().map(converterUtil::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JokeDTO> findAllByParam(JokeSearchDTO jokeSearchDTO){
        List<Joke> jokeList = findAllByParamSwitchCase(jokeSearchDTO);

        return jokeList.stream().map(converterUtil::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JokeDTO> findAllByRandom() {
        List<Joke> jokeList = jokeRepository.findAllByRandomFive();

        return jokeList.stream().map(converterUtil::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JokeDTO save(JokeDTO jokeDTO) {
        Joke joke = converterUtil.convertToEntity(jokeDTO);

        Category category = checkExistingCategory(jokeDTO);
        joke.setCategory(category);

        if(!Objects.isNull(jokeDTO.getId())) joke.setDateTimeUpdated(LocalDateTime.now());

        joke = jokeRepository.save(joke);

        return converterUtil.convertToDTO(joke);
    }

    @Override
    public void deleteById(Long id) {
        jokeRepository.deleteById(id);
    }

    private Category checkExistingCategory(JokeDTO jokeDTO){
        Category category = categoryRepository.findOneByTitle(jokeDTO.getCategoryTitle()).orElse(null);

        if(Objects.isNull(category)){
            category = new Category();
            category.setTitle(jokeDTO.getCategoryTitle());

            categoryRepository.save(category);

            category = categoryRepository.findOneByTitle(jokeDTO.getCategoryTitle()).orElse(null);
        }

        return category;
    }

    private List<Joke> findAllByParamSwitchCase(JokeSearchDTO searchDTO){
        String jokeName = searchDTO.getCategoryTitle();

        switch(searchDTO.getJokeSortEnum().getId()){
            case 1:
                return jokeRepository.findAllByParamNewest(jokeName);
            case 2:
                return jokeRepository.findAllByParamOldest(jokeName);
            default:
                return jokeRepository.findAllByParam(jokeName);
        }
    }
}
