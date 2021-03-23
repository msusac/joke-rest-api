package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "DTO class for searching Joke Categories by their given params.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySearchDTO {

    @ApiModelProperty(
            name = "title",
            notes = "Joke Category Title.",
            example = "Chuck Norris",
            required = true
    )
    @JsonProperty(value = "title", access = JsonProperty.Access.READ_WRITE)
    private String title;
}
