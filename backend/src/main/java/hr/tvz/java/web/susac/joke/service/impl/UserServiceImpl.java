package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.dto.user.LoginDTO;
import hr.tvz.java.web.susac.joke.dto.user.RegisterDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
import hr.tvz.java.web.susac.joke.model.Authority;
import hr.tvz.java.web.susac.joke.model.User;
import hr.tvz.java.web.susac.joke.model.Verification;
import hr.tvz.java.web.susac.joke.repository.AuthorityRepository;
import hr.tvz.java.web.susac.joke.repository.UserRepository;
import hr.tvz.java.web.susac.joke.repository.VerificationRepository;
import hr.tvz.java.web.susac.joke.service.UserService;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import hr.tvz.java.web.susac.joke.util.mail.MailSenderUtil;
import hr.tvz.java.web.susac.joke.util.mail.ActivationMail;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;

    private final ConverterUtil<User, UserDTO> converter;
    private final MailSenderUtil mailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findOneByUsernameEnabled(String username) {
        User user = userRepository.findOneByUsernameAndEnabledTrue(username);

        if(Objects.isNull(user))
            return null;

        return converter.convertToDTO(user);
    }

    @Override
    public Boolean doPasswordsMatch(RegisterDTO registerDTO) {
        if(registerDTO.getPassword().equals(registerDTO.getRepeatPassword()))
            return true;

        return false;
    }

    @Override
    public Boolean isEnabled(LoginDTO loginDTO) {
        Optional<User> user = userRepository.findOneByUsername(loginDTO.getUsername());

        if(user.isPresent())
            return user.get().getEnabled();

        return false;
    }

    @Override
    public Boolean isFreeEmail(RegisterDTO registerDTO) {
        User user = userRepository.findOneByEmail(registerDTO.getEmail());

        if(Objects.isNull(user)) return true;

        return false;
    }

    @Override
    public Boolean isFreeUsername(RegisterDTO registerDTO) {
        Optional<User> user = userRepository.findOneByUsername(registerDTO.getUsername());

        if(!user.isPresent()) return true;

        return false;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());

        Authority authority = authorityRepository.findOneByName("ROLE_USER");
        user.getAuthoritySet().add(authority);

        userRepository.save(user);

        sendActivationMail(user);
    }

    private void sendActivationMail(User user){
        String token = generateVerificationToken(user);

        ActivationMail activationMail = new ActivationMail();
        activationMail.setRecipient(user.getEmail());
        activationMail.setSubject("Please confirm your account!");
        activationMail.setBody("Thank you for signing up on Joke REST App!\n" +
                "Please confirm activate account on the following url!\n" +
                "http://localhost:8090/api/user/accountVerification/" + token + "\n\n" +
                "Account will be deleted if you don't confirm registration within 3 days.");

        mailSender.sendMail(activationMail);
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();

        while(!Objects.isNull(verificationRepository.findOneByTokenNotExpired(token))){
            token = UUID.randomUUID().toString();
        }

        Verification verification = new Verification();
        verification.setToken(token);
        verification.setUser(user);
        verification.setDateExpiry(LocalDate.now().plusDays(3));

        verificationRepository.save(verification);

        return token;
    }
}
