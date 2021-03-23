package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.JokeSearchDTO;

import java.util.List;

public interface JokeService {

    JokeDTO findOneById(Long id);

    List<JokeDTO> findAllByCategory(Long id);

    List<JokeDTO> findAllByParam(JokeSearchDTO searchDTO);

    List<JokeDTO> findAllByRandom();

    JokeDTO save(JokeDTO jokeDTO);

    void deleteById(Long id);
}
