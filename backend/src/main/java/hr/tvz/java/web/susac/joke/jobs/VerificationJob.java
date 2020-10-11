package hr.tvz.java.web.susac.joke.jobs;

import hr.tvz.java.web.susac.joke.model.User;
import hr.tvz.java.web.susac.joke.model.Verification;
import hr.tvz.java.web.susac.joke.repository.UserRepository;
import hr.tvz.java.web.susac.joke.repository.VerificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class VerificationJob extends QuartzJobBean {

    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<Verification> verificationList = verificationRepository.findAllByDateExpiredDayLater();

        for(Verification verification : verificationList){

                verificationRepository.deleteById(verification.getId());

                if(!Objects.isNull(verification.getUser())){
                    User user = verification.getUser();
                    log.info("User" + " " + user.getUsername() + " " + "was deleted due not activating account in given time!");
                    log.info(user.toString());
                    userRepository.deleteById(user.getId());
                }

                log.info("Deleted verification token:" + verification.toString());
        }
    }
}
