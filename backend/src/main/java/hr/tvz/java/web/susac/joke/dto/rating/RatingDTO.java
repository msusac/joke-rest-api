package hr.tvz.java.web.susac.joke.dto.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.tvz.java.web.susac.joke.enums.RatingTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {

    @Positive
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(value = "user", access = JsonProperty.Access.READ_ONLY)
    private String user;

    @JsonProperty(value = "joke_id", access = JsonProperty.Access.READ_ONLY)
    private Integer jokeId;

    @NotNull(message = "Please select Rating Type!")
    @JsonProperty(value = "rating_type", access = JsonProperty.Access.READ_WRITE)
    private RatingTypeEnum ratingTypeEnum;
}
