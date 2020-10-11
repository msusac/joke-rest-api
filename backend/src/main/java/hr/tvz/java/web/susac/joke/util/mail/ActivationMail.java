package hr.tvz.java.web.susac.joke.util.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivationMail {

    private String subject;
    private String recipient;
    private String body;
}
