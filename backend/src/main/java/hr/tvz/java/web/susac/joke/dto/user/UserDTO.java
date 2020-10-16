package hr.tvz.java.web.susac.joke.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Positive
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(value = "username", access = JsonProperty.Access.READ_ONLY)
    private String username;

    @JsonProperty(value = "email", access = JsonProperty.Access.READ_ONLY)
    private String email;

    @JsonProperty(value = "authority", access = JsonProperty.Access.READ_ONLY)
    private String authority;

    @JsonProperty(value = "comment_count", access = JsonProperty.Access.READ_ONLY)
    private Integer commentCount;

    @JsonProperty(value = "joke_count", access = JsonProperty.Access.READ_ONLY)
    private Integer jokeCount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy.")
    @JsonProperty(value = "date_joined", access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateJoined;

    @JsonProperty(value = "enabled", access = JsonProperty.Access.READ_ONLY)
    private Boolean enabled;
}
