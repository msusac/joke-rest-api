package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.search.UserSearchDTO;
import hr.tvz.java.web.susac.joke.dto.user.LoginDTO;
import hr.tvz.java.web.susac.joke.dto.user.RegisterDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findOneByUsernameEnabled(String username);

    List<UserDTO> findAllByParam(UserSearchDTO userSearchDTO);

    Boolean doPasswordsMatch(RegisterDTO registerDTO);
    Boolean isEnabled(LoginDTO loginDTO);
    Boolean isFreeEmail(RegisterDTO registerDTO);
    Boolean isFreeUsername(RegisterDTO registerDTO);

    void register(RegisterDTO registerDTO);
}
