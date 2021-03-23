package hr.tvz.java.web.susac.joke.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JokeSortEnum {

    RELEVANT(0, "RELEVANT"),
    DATE_NEW(1, "ASC"),
    DATE_OLD(2, "DESC");

    private Integer id;
    private String type;
}
