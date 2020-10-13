package hr.tvz.java.web.susac.joke.dto.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDisplayDTO {

    @JsonProperty(value = "liked_count", access = JsonProperty.Access.READ_ONLY)
    private Integer likedCount;

    @JsonProperty(value = "funny_count", access = JsonProperty.Access.READ_ONLY)
    private Integer funnyCount;

    @JsonProperty(value = "wow_count", access = JsonProperty.Access.READ_ONLY)
    private Integer wowCount;

    @JsonProperty(value = "not_funny_count", access = JsonProperty.Access.READ_ONLY)
    private Integer notFunnyCount;
}
