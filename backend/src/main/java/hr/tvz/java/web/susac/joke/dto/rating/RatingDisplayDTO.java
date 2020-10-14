package hr.tvz.java.web.susac.joke.dto.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDisplayDTO {

    @Positive
    @JsonProperty(value = "joke_id", access = JsonProperty.Access.READ_ONLY)
    private Integer jokeId;

    @JsonProperty(value = "liked_count", access = JsonProperty.Access.READ_ONLY)
    private Integer likedCount;

    @JsonProperty(value = "funny_count", access = JsonProperty.Access.READ_ONLY)
    private Integer funnyCount;

    @JsonProperty(value = "wow_count", access = JsonProperty.Access.READ_ONLY)
    private Integer wowCount;

    @JsonProperty(value = "not_funny_count", access = JsonProperty.Access.READ_ONLY)
    private Integer notFunnyCount;
}
