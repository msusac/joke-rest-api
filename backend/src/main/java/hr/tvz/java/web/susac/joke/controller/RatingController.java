package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.rating.RatingDTO;
import hr.tvz.java.web.susac.joke.dto.rating.RatingDisplayDTO;
import hr.tvz.java.web.susac.joke.model.Rating;
import hr.tvz.java.web.susac.joke.service.JokeService;
import hr.tvz.java.web.susac.joke.service.RatingDisplayService;
import hr.tvz.java.web.susac.joke.service.RatingService;
import hr.tvz.java.web.susac.joke.util.validation.ValidationErrorPrinter;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/joke")
@Api(description = "Contains API operations for Joke Rating model.")
public class RatingController {

    private final JokeService jokeService;
    private final RatingService ratingService;
    private final RatingDisplayService ratingDisplayService;

    @ApiOperation(value = "Retrieves User's rating of selected Joke. (ADMIN OR USER ONLY!)",
            notes = "User needs to be logged in to use that function.",
            authorizations = { @Authorization(value="jwtToken")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke Rating]", response = RatingDTO.class),
            @ApiResponse(code = 404, message = "Selected Joke Rating does not exists!", response = void.class)
    })
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}/rating")
    public ResponseEntity<?> getOneByJokeAndUser(@PathVariable("id") Integer id, Principal principal){
        RatingDTO ratingDTO = ratingService.findOneByJokeAndUser(id, principal.getName());

        if(Objects.isNull(ratingDTO))
            return new ResponseEntity<>("Selected Joke Rating does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves total Ratings count of selected Joke.",
            notes = "Ratings with 'NONE' are not counted in.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke Rating list]", response = RatingDisplayDTO.class),
            @ApiResponse(code = 404, message = "Selected Joke does not exists!", response = void.class)
    })
    @GetMapping("/{id}/ratings")
    public ResponseEntity<?> getAllIntoOneByJoke(@PathVariable("id") Integer id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        RatingDisplayDTO ratingDisplayDTO = ratingDisplayService.findAllInOneByJoke(jokeDTO.getId());

        return new ResponseEntity<>(ratingDisplayDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Allows User to rate selected Joke. (ADMIN OR USER ONLY!)",
            notes = "User needs to be logged in to use that function.",
            authorizations = { @Authorization(value="jwtToken")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke Rating]", response = RatingDTO.class),
            @ApiResponse(code = 404, message = "Selected Joke does not exists!", response = void.class),
            @ApiResponse(code = 406, message = "[Failed validation]\n" +
                    "[Error has occurred during operation]", response = void.class)
    })
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{id}/rate")
    public ResponseEntity<?> save(@PathVariable("id") Integer id,
                                  @Valid @RequestBody RatingDTO ratingDTO,
                                  @ApiIgnore Errors errors,
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
