package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySearchDTO {

    @NotNull(message = "Category Search Name must not be null!")
    @JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String name;
}
