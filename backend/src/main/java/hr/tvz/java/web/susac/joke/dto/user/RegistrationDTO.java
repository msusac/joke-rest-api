package hr.tvz.java.web.susac.joke.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {

    @NotNull(message = "Please write down Username!")
    @Size(min = 6, max = 25, message = "Username must be between 6 and 25 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Use only alphanumeric letters without space for Username!")
    @JsonProperty(value = "username", access = JsonProperty.Access.WRITE_ONLY)
    private String username;

    @NotNull(message = "Please write down Password!")
    @Size(min = 6, max = 50, message = "Password must be between 8 and 50 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Use only alphanumeric letters for Password!")
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Please repeat the Password!")
    @JsonProperty(value = "repeat_password", access = JsonProperty.Access.WRITE_ONLY)
    private String repeatPassword;

    @NotNull(message = "Please write down Email!")
    @Email(message = "Please follow the rules of writing down Email correctly!")
    @JsonProperty(value = "email", access = JsonProperty.Access.WRITE_ONLY)
    private String email;
}