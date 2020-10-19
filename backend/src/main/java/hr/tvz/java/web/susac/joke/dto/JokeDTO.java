package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(
        description = "DTO class that represents Joke."
)
public class JokeDTO {

    @ApiModelProperty(
            name = "id",
            notes = "Unique identifier representing selected Joke",
            position = 1,
            example = "3",
            readOnly = true
    )
    @Positive
    @JsonProperty(value = "id")
    private Integer id;

    @ApiModelProperty(
            name = "id",
            notes = "Joke Category",
            position = 2,
            example = "Test Joke",
            required = true
    )
    @NotNull(message = "Category Name must not be null!")
    @NotBlank(message = "Category Name must not be empty!")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Use only alphabet letters for Category Name!")
    @Size(min = 2, max = 80, message = "Category Name size must be between 2 and 80 characters!")
    @JsonProperty(value = "category")
    private String category;

    @ApiModelProperty(
            name = "description",
            notes = "Joke Description",
            position = 3,
            example = "Test Joke",
            required = true
    )
    @NotNull(message = "Joke Description must not be null!")
    @NotBlank(message = "Joke Description must not be empty!")
    @Size(min = 2, max = 500, message = "Joke Description size must be between 2 and 500 characters!")
    @JsonProperty(value = "description")
    private String description;

    @ApiModelProperty(
            name = "user",
            notes = "User",
            position = 4,
            example = "swaggeruser",
            readOnly = true
    )
    @JsonProperty(value = "user")
    private String user;

    @ApiModelProperty(
            name = "comment_count",
            notes = "Total count of Joke Comments",
            position = 5,
            example = "3",
            readOnly = true
    )
    @JsonProperty(value = "comment_count")
    private Integer commentCount;

    @ApiModelProperty(
            name = "vote_count",
            notes = "Total count of Joke Votes",
            position = 6,
            example = "4",
            readOnly = true
    )
    @JsonProperty(value = "vote_count")
    private Integer voteCount;

    @ApiModelProperty(
            name = "date_created",
            notes = "Creation date",
            position = 7,
            example = "10.10.2020",
            readOnly = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_created")
    private LocalDate dateCreated;

    @ApiModelProperty(
            name = "date_updated",
            notes = "Update date",
            position = 8,
            example = "11.10.2020",
            readOnly = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_updated")
    private LocalDate dateUpdated;
}
