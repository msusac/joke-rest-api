package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @Positive
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Positive
    @JsonProperty(value = "parent_id", access = JsonProperty.Access.READ_WRITE)
    private Integer parentId;

    @Positive
    @JsonProperty(value = "joke_id", access = JsonProperty.Access.READ_ONLY)
    private Integer jokeId;

    @JsonProperty(value = "user", access = JsonProperty.Access.READ_ONLY)
    private String user;

    @JsonProperty(value = "reply_to", access = JsonProperty.Access.READ_ONLY)
    private String replyTo;

    @NotNull(message = "Comment must not be null!")
    @NotBlank(message = "Comment must not be empty!")
    @Size(min = 2, max = 500, message = "Comment size must be between 2 and 500 characters!")
    @JsonProperty(value = "description", access = JsonProperty.Access.READ_WRITE)
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy. HH:mm")
    @JsonProperty(value = "date_time_created", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateTimeCreated;
}
