package hr.tvz.java.web.susac.joke.util.converter.impl;

import hr.tvz.java.web.susac.joke.dto.CommentDTO;
import hr.tvz.java.web.susac.joke.model.Comment;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.model.User;
import hr.tvz.java.web.susac.joke.repository.CommentRepository;
import hr.tvz.java.web.susac.joke.repository.JokeRepository;
import hr.tvz.java.web.susac.joke.repository.UserRepository;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
@Qualifier("CommentConverter")
public class CommentConverterImpl implements ConverterUtil<Comment, CommentDTO> {

    private final CommentRepository commentRepository;
    private final JokeRepository jokeRepository;
    private final UserRepository userRepository;

    private ModelMapper mapper;

    @Override
    public CommentDTO convertToDTO(Comment entity) {
        CommentDTO commentDTO = mapper.map(entity, CommentDTO.class);
        commentDTO.setUser(entity.getUser().getUsername());
        commentDTO.setDescription(entity.getDescription());
        commentDTO.setDateTimeCreated(entity.getDateTimeCreated());

        if(!Objects.isNull(entity.getParent())){
            commentDTO.setParentId(entity.getParent().getId());
            commentDTO.setReplyTo(entity.getParent().getUser().getUsername());
        }

        return commentDTO;
    }

    @Override
    public Comment convertToEntity(CommentDTO dto) {
        Comment comment = mapper.map(dto, Comment.class);
        comment.setDescription(dto.getDescription());
        comment.setDateTimeCreated(dto.getDateTimeCreated());

        Comment parentComment = commentRepository.findOneById(dto.getParentId());
        Joke joke = jokeRepository.findOneById(dto.getJokeId());
        User user = userRepository.findOneByUsername(dto.getUser()).get();

        if(!Objects.isNull(parentComment)){
            comment.setParent(parentComment);
        }

        comment.setJoke(joke);
        comment.setUser(user);

        return comment;
    }
}
