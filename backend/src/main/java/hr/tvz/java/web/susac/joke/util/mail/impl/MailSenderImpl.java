package hr.tvz.java.web.susac.joke.util.mail.impl;

import hr.tvz.java.web.susac.joke.util.mail.ActivationMail;
import hr.tvz.java.web.susac.joke.util.mail.MailSenderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@Qualifier("MailSender")
public class MailSenderImpl implements MailSenderUtil {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendMail(ActivationMail activationMail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("mymail@email.com");
            messageHelper.setTo(activationMail.getRecipient());
            messageHelper.setSubject(activationMail.getSubject());
            messageHelper.setText(activationMail.getBody());
        };

        try{
            mailSender.send(messagePreparator);
            log.info("Activation Mail successfully sent!");
        }
        catch(MailException e){
            log.error("Error has occurred while sending mail!");
            log.error(e.getMessage());
        }
    }
}
