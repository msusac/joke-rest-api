package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.rating.RatingDTO;
import hr.tvz.java.web.susac.joke.dto.rating.RatingDisplayDTO;
import hr.tvz.java.web.susac.joke.service.JokeService;
import hr.tvz.java.web.susac.joke.service.RatingDisplayService;
import hr.tvz.java.web.susac.joke.service.RatingService;
import hr.tvz.java.web.susac.joke.util.validation.ValidationErrorPrinter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/joke")
public class RatingController {

    private final JokeService jokeService;
    private final RatingService ratingService;
    private final RatingDisplayService ratingDisplayService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}/rating")
    public ResponseEntity<?> getOneByJokeAndUser(@PathVariable("id") Integer id, Principal principal){
        RatingDTO ratingDTO = ratingService.findOneByJokeAndUser(id, principal.getName());

        if(Objects.isNull(ratingDTO))
            return new ResponseEntity<>("Selected Joke Rating does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<?> getAllIntoOneByJoke(@PathVariable("id") Integer id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        RatingDisplayDTO ratingDisplayDTO = ratingDisplayService.findAllInOneByJoke(jokeDTO.getId());

        return new ResponseEntity<>(ratingDisplayDTO, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{id}/rate")
    public ResponseEntity<?> save(@PathVariable("id") Integer id,
                                  @Valid @RequestBody RatingDTO ratingDTO,
                                  Errors errors,
                                  Principal principal){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);

        try{
            ratingDTO.setJokeId(id);
            ratingDTO.setUser(principal.getName());
            ratingDTO = ratingService.save(ratingDTO);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
    }
}
