package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.dto.rating.RatingDTO;
import hr.tvz.java.web.susac.joke.model.Rating;
import hr.tvz.java.web.susac.joke.repository.RatingRepository;
import hr.tvz.java.web.susac.joke.service.RatingService;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final ConverterUtil<Rating, RatingDTO> converter;

    @Override
    public RatingDTO findOneByJokeAndUser(Integer jokeId, String username) {
        Rating rating = ratingRepository.findOneByJokeIdAndUsername(jokeId, username);

        if(Objects.isNull(rating))
            return null;

        return converter.convertToDTO(rating);
    }

    @Override
    public RatingDTO save(RatingDTO ratingDTO) {
        Rating rating = converter.convertToEntity(ratingDTO);

        ratingRepository.save(rating);

        return converter.convertToDTO(rating);
    }
}
