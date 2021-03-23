package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@ApiModel(description  = "DTO class that represents selected Joke.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JokeDTO {

    @ApiModelProperty(
            name = "id",
            notes = "Unique identifier of selected Joke.",
            example = "5",
            position = 1,
            readOnly = true)
    @Positive
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(
            name = "categoryTitle",
            notes = "Joke Category Title.",
            position = 2,
            example = "Chuck Norris",
            required = true
    )
    @NotBlank(message = "Joke Name must not be empty!")
    @NotNull(message = "Joke Name must not be null!")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Use only alphabet letters for Joke Name!")
    @Size(min = 3, max = 80, message = "Joke Name size must be between 3 and 80 characters!")
    @JsonProperty(value = "categoryTitle", access = JsonProperty.Access.READ_WRITE)
    private String categoryTitle;

    @ApiModelProperty(
            name = "description",
            notes = "Joke Description.",
            position = 3,
            example = "Chuck Norris",
            required = true
    )
    @NotBlank(message = "Joke Description must not be empty!")
    @NotNull(message = "Joke Description must not be null!")
    @Size(min = 5, max = 500, message = "Joke Description size must be between 5 and 500 characters!")
    @JsonProperty(value = "description", access = JsonProperty.Access.READ_WRITE)
    private String description;

    @ApiModelProperty(
            name = "createdAt",
            notes = "Creation date and time.",
            example = "2021-03-17T19:39:21.56507",
            position = 4,
            readOnly = true)
    @JsonProperty(value = "createdAt", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateTimeCreated;

    @ApiModelProperty(
            name = "updatedAt",
            notes = "Update date and time.",
            example = "2021-03-18T19:39:21.56507",
            position = 5,
            readOnly = true)
    @JsonProperty(value = "updatedAt", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateTimeUpdated;
}
