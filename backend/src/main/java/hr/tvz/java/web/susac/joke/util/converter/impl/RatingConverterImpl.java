package hr.tvz.java.web.susac.joke.util.converter.impl;

import hr.tvz.java.web.susac.joke.dto.rating.RatingDTO;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.model.Rating;
import hr.tvz.java.web.susac.joke.model.User;
import hr.tvz.java.web.susac.joke.repository.JokeRepository;
import hr.tvz.java.web.susac.joke.repository.RatingRepository;
import hr.tvz.java.web.susac.joke.repository.UserRepository;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@AllArgsConstructor
@Qualifier("RatingConverter")
public class RatingConverterImpl implements ConverterUtil<Rating, RatingDTO> {

    private final JokeRepository jokeRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Override
    public RatingDTO convertToDTO(Rating entity) {
        RatingDTO ratingDTO = mapper.map(entity, RatingDTO.class);
        ratingDTO.setRatingTypeEnum(entity.getType());
        ratingDTO.setJokeId(entity.getJoke().getId());
        ratingDTO.setUser(entity.getUser().getUsername());

        return ratingDTO;
    }

    @Override
    public Rating convertToEntity(RatingDTO dto) {
        Rating rating = mapper.map(dto, Rating.class);
        rating.setType(dto.getRatingTypeEnum());

        Rating existingRating = ratingRepository.findOneByJokeIdAndUsername(dto.getJokeId(), dto.getUser());

        if(!Objects.isNull(existingRating)){
            rating.setId(existingRating.getId());
            rating.setDateTimeCreated(existingRating.getDateTimeCreated());
            rating.setDateTimeUpdated(LocalDateTime.now());
        }

        Joke joke = jokeRepository.findOneById(dto.getJokeId());
        User user = userRepository.findOneByUsername(dto.getUser()).get();

        rating.setJoke(joke);
        rating.setUser(user);

        return rating;
    }
}
