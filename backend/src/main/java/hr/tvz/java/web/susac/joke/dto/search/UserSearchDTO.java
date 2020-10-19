package hr.tvz.java.web.susac.joke.dto.search;

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
        description = "DTO class that searches Users by it's given params."
)
public class UserSearchDTO {

    @ApiModelProperty(
            name = "username",
            notes = "Username",
            position = 1,
            example = "admin"
    )
    @NotNull(message = "Username Search must not be null!")
    @JsonProperty(value = "username")
    private String username;

    @ApiModelProperty(
            notes = "E-mail address",
            position = 2,
            example = "admin@admin.com"
    )
    @NotNull(message = "E-mail Search must not be null!")
    @JsonProperty(value = "email")
    private String email;
}
