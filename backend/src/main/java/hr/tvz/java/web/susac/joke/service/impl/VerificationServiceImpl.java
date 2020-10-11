package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.model.User;
import hr.tvz.java.web.susac.joke.model.Verification;
import hr.tvz.java.web.susac.joke.repository.UserRepository;
import hr.tvz.java.web.susac.joke.repository.VerificationRepository;
import hr.tvz.java.web.susac.joke.service.VerificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class VerificationServiceImpl implements VerificationService {

    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;

    @Override
    public Boolean isValid(String token) {
        Verification verification = verificationRepository.findOneByTokenNotExpired(token);

        if(!Objects.isNull(verification)){
            User user = verification.getUser();

            user.setEnabled(true);
            userRepository.save(user);

            verification.setVerified(true);
            verificationRepository.save(verification);

            return true;
        }

        return false;
    }
}
