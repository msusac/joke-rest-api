package hr.tvz.java.web.susac.joke.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotNull(message = "Please write down Username!")
    @JsonProperty(value = "username", access = JsonProperty.Access.READ_WRITE)
    private String username;

    @NotNull(message = "Please write down Password!")
    @JsonProperty(value = "password", access = JsonProperty.Access.READ_WRITE)
    private String password;
}
