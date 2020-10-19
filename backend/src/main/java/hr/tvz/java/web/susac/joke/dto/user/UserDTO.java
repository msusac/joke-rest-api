package hr.tvz.java.web.susac.joke.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(
        description = "DTO class that represents User."
)
public class UserDTO {

    @ApiModelProperty(
            name = "id",
            notes = "Unique identifier of selected User",
            position = 1,
            example = "7",
            readOnly = true
    )
    @Positive
    @JsonProperty(value = "id")
    private Integer id;

    @ApiModelProperty(
            name = "username",
            notes = "Username",
            position = 2,
            example = "usersample",
            readOnly = true
    )
    @JsonProperty(value = "username")
    private String username;

    @ApiModelProperty(
            name = "email",
            notes = "Email address",
            position = 3,
            example = "usersample@user.com",
            readOnly = true
    )
    @JsonProperty(value = "email")
    private String email;

    @ApiModelProperty(
            name = "authority",
            notes = "User Authority",
            position = 4,
            example = "User",
            readOnly = true
    )
    @JsonProperty(value = "authority")
    private String authority;

    @ApiModelProperty(
            name = "comment_count",
            notes = "Total count of User comments",
            position = 5,
            example = "14",
            readOnly = true
    )
    @JsonProperty(value = "comment_count")
    private Integer commentCount;

    @ApiModelProperty(
            name = "joke_count",
            notes = "Total count of User jokes",
            position = 6,
            example = "11",
            readOnly = true
    )
    @JsonProperty(value = "joke_count")
    private Integer jokeCount;

    @ApiModelProperty(
            name = "date_created",
            notes = "Join date",
            position = 7,
            example = "10.10.2020",
            readOnly = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_joined")
    private LocalDate dateJoined;

    @ApiModelProperty(
            name = "enabled",
            notes = "Is user activated?",
            position = 8,
            example = "true",
            readOnly = true
    )
    @JsonProperty(value = "enabled")
    private Boolean enabled;
}
