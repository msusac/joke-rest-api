package hr.tvz.java.web.susac.joke.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySearchDTO {

    @JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String name;
}
