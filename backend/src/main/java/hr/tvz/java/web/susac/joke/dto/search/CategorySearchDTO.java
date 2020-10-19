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
        description = "DTO class that searches Jokes/Categories by it's given params."
)
public class CategorySearchDTO {

    @ApiModelProperty(
            name = "name",
            notes = "Category name",
            position = 1,
            example = "Test Joke"
    )
    @NotNull(message = "Category Search Name must not be null!")
    @JsonProperty(value = "name")
    private String name;
}
