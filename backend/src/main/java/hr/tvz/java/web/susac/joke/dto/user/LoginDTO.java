package hr.tvz.java.web.susac.joke.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(
        description = "DTO class that's used for User Authentication."
)
public class LoginDTO {

    @ApiModelProperty(
            name = "username",
            notes = "Username",
            position = 1,
            example = "admin",
            required = true
    )
    @NotNull(message = "Please write down Username!")
    @JsonProperty(value = "username")
    private String username;

    @ApiModelProperty(
            name = "password",
            notes = "Password",
            position = 2,
            example = "admin",
            required = true
    )
    @NotNull(message = "Please write down Password!")
    @JsonProperty(value = "password")
    private String password;
}
