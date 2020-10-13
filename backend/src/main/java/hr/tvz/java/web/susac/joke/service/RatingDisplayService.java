package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.rating.RatingDisplayDTO;

public interface RatingDisplayService {

    RatingDisplayDTO findAllInOneByJoke(Integer id);
}
