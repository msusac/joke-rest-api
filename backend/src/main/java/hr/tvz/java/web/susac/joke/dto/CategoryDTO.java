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
        description = "DTO class that represents Joke Category."
)
public class CategoryDTO {

    @ApiModelProperty(
            name = "id",
            notes = "Unique identifier of selected Joke Category",
            position = 1,
            example = "5",
            readOnly = true
    )
    @Positive
    @JsonProperty(value = "id")
    private Integer id;

    @ApiModelProperty(
            name = "name",
            notes = "Category name",
            position = 2,
            example = "Test Joke",
            required = true
    )
    @NotBlank(message = "Category Name must not be empty!")
    @NotNull(message = "Category Name must not be null!")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Use only alphabet letters for Category Name!")
    @Size(min = 2, max = 80, message = "Category Name size must be between 2 and 80 characters!")
    @JsonProperty(value = "name")
    private String name;

    @ApiModelProperty(
            name = "joke_count",
            notes = "Joke Count",
            position = 3,
            example = "10",
            readOnly = true
    )
    @JsonProperty(value = "joke_count")
    private Integer jokeCount;

    @ApiModelProperty(
            name = "date_created",
            notes = "Creation date",
            position = 4,
            example = "10.10.2020.",
            readOnly = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_created")
    private LocalDate dateCreated;

    @ApiModelProperty(
            name = "date_updated",
            notes = "Update date",
            position = 5,
            example = "11.10.2020.",
            readOnly = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_updated")
    private LocalDate dateUpdated;
}
