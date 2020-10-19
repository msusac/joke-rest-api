package hr.tvz.java.web.susac.joke.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(
        description = "DTO class that's used for User Registration."
)
public class RegisterDTO {

    @ApiModelProperty(
            name = "username",
            notes = "Username",
            position = 1,
            example = "swaggeruser",
            required = true
    )
    @NotNull(message = "Please write down Username!")
    @NotBlank(message = "Username must not be empty!")
    @Size(min = 6, max = 25, message = "Username must be between 6 and 25 characters!")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Use only alphanumeric letters without space for Username!")
    @JsonProperty(value = "username")
    private String username;

    @ApiModelProperty(
            name = "password",
            notes = "Password",
            position = 2,
            example = "swaggeruser",
            required = true
    )
    @NotNull(message = "Please write down Password!")
    @Size(min = 6, max = 50, message = "Password must be between 8 and 50 characters!")
    @Pattern(regexp = "[A-Za-z0-9]+$", message = "Use only alphanumeric letters for Password!")
    @JsonProperty(value = "password")
    private String password;

    @ApiModelProperty(
            name = "repeat_password",
            notes = "Repeat Password",
            position = 1,
            example = "swaggeruser",
            required = true
    )
    @NotNull(message = "Please repeat the Password!")
    @JsonProperty(value = "repeat_password")
    private String repeatPassword;

    @ApiModelProperty(
            name = "email",
            notes = "Email address",
            position = 4,
            example = "swagger@user.com",
            required = true
    )
    @NotNull(message = "Please write down Email!")
    @NotBlank(message = "E-mail address must not be empty!")
    @Email(message = "Please follow the rules of writing down Email correctly!")
    @JsonProperty(value = "email")
    private String email;
}
