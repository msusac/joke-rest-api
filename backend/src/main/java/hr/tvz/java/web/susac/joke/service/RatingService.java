package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.rating.RatingDTO;

public interface RatingService {

    RatingDTO findOneByJokeAndUser(Integer jokeId, String username);

    RatingDTO save(RatingDTO ratingDTO);
}
