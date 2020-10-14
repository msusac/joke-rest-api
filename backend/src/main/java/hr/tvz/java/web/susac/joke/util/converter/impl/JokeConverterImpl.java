package hr.tvz.java.web.susac.joke.util.converter.impl;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.repository.JokeRepository;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@AllArgsConstructor
@Qualifier("JokeConverter")
public class JokeConverterImpl implements ConverterUtil<Joke, JokeDTO> {

    private final JokeRepository jokeRepository;

    private final ModelMapper mapper;

    @Override
    public JokeDTO convertToDTO(Joke entity) {
        JokeDTO jokeDTO = mapper.map(entity, JokeDTO.class);
        jokeDTO.setUser(entity.getUser().getUsername());
        jokeDTO.setCategory(entity.getCategory().getName());
        jokeDTO.setDateCreated(entity.getDateTimeCreated().toLocalDate());
        jokeDTO.setCommentCount(entity.getCommentList().size());
        jokeDTO.setVoteCount(entity.getRatingList().size());

        if(!Objects.isNull(entity.getDateTimeUpdated())) {
            jokeDTO.setDateUpdated(entity.getDateTimeUpdated().toLocalDate());
        }

        return jokeDTO;
    }

    @Override
    public Joke convertToEntity(JokeDTO dto) {
        Joke joke = mapper.map(dto, Joke.class);

        if(!Objects.isNull(joke.getId())){
            Joke existingJoke = jokeRepository.findOneById(joke.getId());
            joke.setCategory(existingJoke.getCategory());
            joke.setDateTimeCreated(existingJoke.getDateTimeCreated());
        }

        return joke;
    }
}
