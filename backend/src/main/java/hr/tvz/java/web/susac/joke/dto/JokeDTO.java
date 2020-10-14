package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JokeDTO {

    @Positive
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull(message = "Category Name must not be null!")
    @NotBlank(message = "Category Name must not be empty!")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Use only alphabet letters for Category Name!")
    @Size(min = 2, max = 80, message = "Category Name size must be between 2 and 80 characters!")
    @JsonProperty(value = "category", access = JsonProperty.Access.READ_WRITE)
    private String category;

    @NotNull(message = "Joke Description must not be null!")
    @NotBlank(message = "Joke Description must not be empty!")
    @Size(min = 2, max = 500, message = "Joke Description size must be between 2 and 500 characters!")
    @JsonProperty(value = "description", access = JsonProperty.Access.READ_WRITE)
    private String description;

    @JsonProperty(value = "user", access = JsonProperty.Access.READ_ONLY)
    private String user;

    @JsonProperty(value = "comment_count", access = JsonProperty.Access.READ_ONLY)
    private Integer commentCount;

    @JsonProperty(value = "vote_count", access = JsonProperty.Access.READ_ONLY)
    private Integer voteCount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_created", access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_updated", access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateUpdated;
}
