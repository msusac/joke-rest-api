package hr.tvz.java.web.susac.joke.dto.rating;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import hr.tvz.java.web.susac.joke.enums.RatingTypeEnum;
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_created", access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_updated", access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateUpdated;
}
