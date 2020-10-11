package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.service.JokeService;
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
@RequestMapping(value = "/api/joke")
public class JokeController {

    private final JokeService jokeService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JokeDTO> jokeDTOList = jokeService.findAllLatest();

        if(CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Joke list is empty!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Integer id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(jokeDTO, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> getAllByParam(@RequestBody CategorySearchDTO categorySearchDTO){
        List<JokeDTO> jokeDTOList = jokeService.findAllByParam(categorySearchDTO);

        if(CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Joke list is empty!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody JokeDTO jokeDTO, Errors errors, Principal principal){
        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);

        try{
            jokeDTO.setUser(principal.getName());
            jokeDTO = jokeService.save(jokeDTO);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(jokeDTO, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @Valid @RequestBody JokeDTO updateDTO, Errors errors){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);


        try{
            updateDTO.setId(id);
            jokeService.save(updateDTO);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        updateDTO = jokeService.findOneById(id);
        return new ResponseEntity<>(updateDTO, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        try{
            jokeService.deleteById(id);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("The following" + " " + jokeDTO.getCategory() + " " + "joke was deleted!\n"
                + jokeDTO.getDescription(), HttpStatus.OK
        );
    }
}
