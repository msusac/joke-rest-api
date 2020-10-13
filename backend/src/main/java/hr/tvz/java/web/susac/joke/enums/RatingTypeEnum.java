package hr.tvz.java.web.susac.joke.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RatingTypeEnum {

    LIKED(0, "Liked"),
    FUNNY(1, "Funny"),
    WOW(2, "Wow"),
    NOT_FUNNY(3, "Not Funny"),
    NONE(4,"None");

    private Integer number;
    private String type;
}
