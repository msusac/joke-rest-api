package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.CommentDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
import hr.tvz.java.web.susac.joke.service.CommentService;
import hr.tvz.java.web.susac.joke.service.JokeService;
import hr.tvz.java.web.susac.joke.service.UserService;
import hr.tvz.java.web.susac.joke.util.validation.ValidationErrorPrinter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
public class CommentController {

    private final CommentService commentService;
    private final JokeService jokeService;
    private final UserService userService;

    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getOneById(@PathVariable("id") Integer id){
        CommentDTO commentDTO = commentService.findOneById(id);

        if(Objects.isNull(commentDTO))
            return new ResponseEntity<>("Selected Comment does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @GetMapping("/joke/{id}/comments")
    public ResponseEntity<?> getAllByJoke(@PathVariable("id") Integer id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        List<CommentDTO> commentDTOList = commentService.findAllByJoke(jokeDTO.getId());

        if(CollectionUtils.isEmpty(commentDTOList))
            return new ResponseEntity<>("Joke has not comments!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/{username}/comments")
    public ResponseEntity<?> getAllByJoke(@PathVariable("username") String username){
        UserDTO userDTO = userService.findOneByUsernameEnabled(username);

        if(Objects.isNull(userDTO))
            return new ResponseEntity<>("Selected User does not exists!", HttpStatus.NOT_FOUND);

        List<CommentDTO> commentDTOList = commentService.findAllByUser(userDTO.getId());

        if(CollectionUtils.isEmpty(commentDTOList))
            return new ResponseEntity<>("User has no comments!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/joke/{id}/comment")
    public ResponseEntity<?> save(@PathVariable("id") Integer id,
                                  @Valid @RequestBody CommentDTO commentDTO,
                                  Errors errors,
                                  Principal principal){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);

        try{
            commentDTO.setJokeId(id);
            commentDTO.setUser(principal.getName());
            commentDTO = commentService.save(commentDTO);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        CommentDTO commentDTO = commentService.findOneById(id);

        if(Objects.isNull(commentDTO))
            return new ResponseEntity<>("Selected Comment does not exists!", HttpStatus.NOT_FOUND);

        try{
            commentService.deleteById(commentDTO.getId());
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("Comment was successfully deleted!", HttpStatus.OK);
    }
}
