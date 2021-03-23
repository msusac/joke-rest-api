package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "DTO class that represents Joke Category.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @ApiModelProperty(
                name = "id",
                notes = "Unique identifier of selected Joke Category.",
                example = "5",
                position = 1,
                readOnly = true)
    @Positive
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(
            name = "title",
            notes = "Joke Category Title.",
            example = "Chuck Norris",
            position = 2,
            required = true)
    @JsonProperty(value = "title", access = JsonProperty.Access.READ_WRITE)
    private String title;

    @ApiModelProperty(
            name = "jokeCount",
            notes = "Total number of jokes in selected Joke Category.",
            example = "7",
            position = 3,
            readOnly = true)
    @JsonProperty(value = "jokeCount", access = JsonProperty.Access.READ_ONLY)
    private Integer jokeCount;

    @ApiModelProperty(
            name = "createdAt",
            notes = "Creation date and time.",
            example = "2021-03-16T19:39:21.56507",
            position = 4,
            readOnly = true)
    @JsonProperty(value = "createdAt", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateTimeCreated;

    @ApiModelProperty(
            name = "createdAt",
            notes = "Update date and time.",
            example = "2021-03-17T19:39:21.56507",
            position = 5,
            readOnly = true)
    @JsonProperty(value = "updatedAt", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateTimeUpdated;

    @ApiModelProperty(
            name = "jokeList",
            notes = "Displays a list of jokes that are associated to selected Joke Category.",
            position = 6,
            readOnly = true)
    @JsonProperty(value = "jokeList", access = JsonProperty.Access.READ_ONLY)
    private List<JokeDTO> jokeDTOList = new ArrayList<>();
}
