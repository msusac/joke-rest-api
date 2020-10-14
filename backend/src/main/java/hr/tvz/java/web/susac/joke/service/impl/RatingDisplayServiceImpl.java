package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.dto.rating.RatingDisplayDTO;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.repository.JokeRepository;
import hr.tvz.java.web.susac.joke.repository.RatingRepository;
import hr.tvz.java.web.susac.joke.service.RatingDisplayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class RatingDisplayServiceImpl implements RatingDisplayService {

    private final JokeRepository jokeRepository;
    private final RatingRepository ratingRepository;

    @Override
    public RatingDisplayDTO findAllInOneByJoke(Integer id) {
        Joke joke = jokeRepository.findOneById(id);

        if(Objects.isNull(joke)) return null;

        RatingDisplayDTO ratingDisplayDTO = new RatingDisplayDTO();
        ratingDisplayDTO.setJokeId(joke.getId());
        ratingDisplayDTO.setFunnyCount(ratingRepository.findAllFunnyByJokeId(id).size());
        ratingDisplayDTO.setLikedCount(ratingRepository.findAllLikedByJokeId(id).size());
        ratingDisplayDTO.setWowCount(ratingRepository.findAllWowByJokeId(id).size());
        ratingDisplayDTO.setNotFunnyCount(ratingRepository.findAllNotFunnyByJokeId(id).size());

        return ratingDisplayDTO;
    }
}
