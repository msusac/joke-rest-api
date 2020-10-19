package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(
        description = "DTO class that represents Joke Comment."
)
public class CommentDTO {

    @ApiModelProperty(
            name = "id",
            notes = "Unique identifier representing selected Joke Comment",
            position = 1,
            example = "6",
            readOnly = true
    )
    @Positive
    @JsonProperty(value = "id")
    private Integer id;

    @ApiModelProperty(
            name = "parent_comment_id",
            notes = "Unique identifier of it's Comment parent",
            position = 2,
            example = "5"
    )
    @Positive
    @JsonProperty(value = "parent_comment_id")
    private Integer parentCommentId;

    @ApiModelProperty(
            name = "joke_id",
            notes = "Unique identifier of selected Joke",
            position = 3,
            example = "2",
            readOnly = true
    )
    @Positive
    @JsonProperty(value = "joke_id")
    private Integer jokeId;

    @ApiModelProperty(
            name = "user",
            notes = "User that posted comment",
            position = 4,
            example = "admin",
            readOnly = true
    )
    @JsonProperty(value = "user")
    private String user;

    @ApiModelProperty(
            name = "reply_to",
            notes = "User that receives reply",
            position = 5,
            example = "testuser",
            readOnly = true
    )
    @JsonProperty(value = "reply_to")
    private String replyTo;

    @ApiModelProperty(
            name = "description",
            notes = "Comment Description",
            position = 6,
            example = "Hello world!",
            required = true
    )
    @NotNull(message = "Comment must not be null!")
    @NotBlank(message = "Comment must not be empty!")
    @Size(min = 2, max = 500, message = "Comment size must be between 2 and 500 characters!")
    @JsonProperty(value = "description")
    private String description;

    @ApiModelProperty(
            name = "date_time_created",
            notes = "Creation date and time",
            position = 7,
            example = "11.10.2020 12:00",
            readOnly = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy. HH:mm")
    @JsonProperty(value = "date_time_created")
    private LocalDateTime dateTimeCreated;
}
