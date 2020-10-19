package hr.tvz.java.web.susac.joke.dto.rating;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import hr.tvz.java.web.susac.joke.enums.RatingTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(
        description = "DTO class that represents Joke Rating."
)
public class RatingDTO {

    @ApiModelProperty(
            name = "id",
            notes = "Unique identifier of selected Joke Rating",
            position = 1,
            example = "10",
            readOnly = true
    )
    @Positive
    @JsonProperty(value = "id")
    private Integer id;

    @ApiModelProperty(
            name = "user",
            notes = "User",
            position = 2,
            example = "testuser",
            readOnly = true
    )
    @JsonProperty(value = "user")
    private String user;

    @ApiModelProperty(
            name = "joke_id",
            notes = "Unique identifier of selected Joke",
            position = 3,
            example = "8",
            readOnly = true
    )
    @JsonProperty(value = "joke_id")
    private Integer jokeId;

    @ApiModelProperty(
            name = "rating_type",
            notes = "Rating Type",
            position = 4,
            example = "FUNNY",
            required = true
    )
    @NotNull(message = "Please select Rating Type!")
    @JsonProperty(value = "rating_type")
    private RatingTypeEnum ratingType;

    @ApiModelProperty(
            name = "date_created",
            notes = "Creation date",
            position = 5,
            example = "12.10.2020.",
            readOnly = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_created")
    private LocalDate dateCreated;

    @ApiModelProperty(
            name = "date_updated",
            notes = "Update date",
            position = 6,
            example = "13.10.2020.",
            readOnly = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_updated")
    private LocalDate dateUpdated;
}
