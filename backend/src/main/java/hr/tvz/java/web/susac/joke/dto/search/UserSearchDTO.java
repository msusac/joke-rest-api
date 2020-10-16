package hr.tvz.java.web.susac.joke.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDTO {

    @NotNull(message = "Username Search must not be null!")
    @JsonProperty(value = "username", access = JsonProperty.Access.READ_WRITE)
    private String username;

    @NotNull(message = "E-mail Search must not be null!")
    @JsonProperty(value = "email", access = JsonProperty.Access.READ_WRITE)
    private String email;
}
