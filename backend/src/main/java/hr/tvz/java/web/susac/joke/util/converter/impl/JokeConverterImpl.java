package hr.tvz.java.web.susac.joke.util.converter.impl;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.repository.JokeRepository;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
@Qualifier("JokeConverter")
public class JokeConverterImpl implements ConverterUtil<Joke, JokeDTO> {

    private final JokeRepository jokeRepository;

    private final ModelMapper modelMapper;

    public JokeDTO convertToDTO(Joke joke){
        JokeDTO jokeDTO = modelMapper.map(joke, JokeDTO.class);
        jokeDTO.setCategoryTitle(joke.getCategory().getTitle());

        return jokeDTO;
    }

    public Joke convertToEntity(JokeDTO jokeDTO){
        Joke joke = modelMapper.map(jokeDTO, Joke.class);

        if(!Objects.isNull(joke.getId())){
            Joke jokeOld = jokeRepository.findOneById(joke.getId()).orElse(null);
            joke.setCategory(jokeOld.getCategory());
            joke.setDateTimeCreated(jokeOld.getDateTimeCreated());
        }

        return joke;
    }
}
