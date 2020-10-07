package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.user.LoginDTO;

public interface UserService {

    Boolean isEnabled(LoginDTO loginDTO);
}
