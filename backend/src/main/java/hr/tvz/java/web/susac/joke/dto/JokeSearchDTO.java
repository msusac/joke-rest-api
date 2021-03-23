package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.tvz.java.web.susac.joke.enums.JokeSortEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "DTO class for searching Joke by their given params.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JokeSearchDTO {

    @ApiModelProperty(
            name = "categoryTitle",
            notes = "Joke Category Title.",
            example = "Chuck Norris",
            position = 1,
            required = true
    )
    @JsonProperty(value = "categoryTitle", access = JsonProperty.Access.READ_WRITE)
    private String categoryTitle;

    @ApiModelProperty(
            name = "jokeSortEnum",
            notes = "Joke Sorting Types:\n 0 - Relevant \n 1 - Newest \n 2 - Oldest",
            example = "0",
            position = 1,
            required = true
    )
    @JsonProperty(value = "sortType")
    private JokeSortEnum jokeSortEnum;
}
