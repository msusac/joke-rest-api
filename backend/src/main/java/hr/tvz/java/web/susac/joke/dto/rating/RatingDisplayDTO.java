package hr.tvz.java.web.susac.joke.dto.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(
        description = "DTO class that represents total rating counts of selected Joke."
)
public class RatingDisplayDTO {

    @ApiModelProperty(
            name = "joke_id",
            notes = "Unique identifier of selected Joke",
            position = 1,
            example = "4",
            readOnly = true
    )
    @Positive
    @JsonProperty(value = "joke_id")
    private Integer jokeId;

    @ApiModelProperty(
            name = "liked_count",
            notes = "Total count of Liked ratings",
            position = 2,
            example = "5",
            readOnly = true
    )
    @JsonProperty(value = "liked_count")
    private Integer likedCount;

    @ApiModelProperty(
            name = "funny_count",
            notes = "Total count of Funny ratings",
            position = 3,
            example = "2",
            readOnly = true
    )
    @JsonProperty(value = "funny_count")
    private Integer funnyCount;

    @ApiModelProperty(
            name = "wow_count",
            notes = "Total count of Funny ratings",
            position = 4,
            example = "4",
            readOnly = true
    )
    @JsonProperty(value = "wow_count")
    private Integer wowCount;

    @ApiModelProperty(
            name = "not_funny_count",
            notes = "Total count of Not Funny ratings",
            position = 5,
            example = "1",
            readOnly = true
    )
    @JsonProperty(value = "not_funny_count")
    private Integer notFunnyCount;
}
