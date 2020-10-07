package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.dto.user.LoginDTO;
import hr.tvz.java.web.susac.joke.model.User;
import hr.tvz.java.web.susac.joke.repository.UserRepository;
import hr.tvz.java.web.susac.joke.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Boolean isEnabled(LoginDTO loginDTO) {
        User user = userRepository.findOneByUsername(loginDTO.getUsername()).get();

        return user.getEnabled();
    }
}
