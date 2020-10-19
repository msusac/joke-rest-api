package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.dto.CommentDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
import hr.tvz.java.web.susac.joke.model.Comment;
import hr.tvz.java.web.susac.joke.repository.CommentRepository;
import hr.tvz.java.web.susac.joke.service.CommentService;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final ConverterUtil<Comment, CommentDTO> converter;

    @Override
    public CommentDTO findOneById(Integer id) {
        Comment comment = commentRepository.findOneById(id);

        if(Objects.isNull(comment)) return null;

        return converter.convertToDTO(comment);
    }

    @Override
    public CommentDTO findOneByIdAndJoke(Integer commentId, Integer jokeId) {
        Comment comment = commentRepository.findOneByIdAndJoke_Id(commentId, jokeId);

        if(Objects.isNull(comment)) return null;

        return converter.convertToDTO(comment);
    }

    @Override
    public List<CommentDTO> findAllByJoke(Integer id) {
        List<Comment> commentList = commentRepository.findAllNewestByJokeId(id);

        return commentList.stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> findAllByUser(Integer id){
        List<Comment> commentList = commentRepository.findAllNewestByUserId(id);

        return commentList.stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        Comment comment = converter.convertToEntity(commentDTO);

        comment = commentRepository.save(comment);

        return converter.convertToDTO(comment);
    }

    @Override
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }
}
