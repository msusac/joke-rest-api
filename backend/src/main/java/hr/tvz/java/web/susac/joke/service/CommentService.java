package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.CommentDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;

import java.util.List;

public interface CommentService {

    CommentDTO findOneById(Integer id);

    List<CommentDTO> findAllByJoke(Integer id);
    List<CommentDTO> findAllByUser(Integer id);

    CommentDTO save(CommentDTO commentDTO);

    void deleteById(Integer id);
}
