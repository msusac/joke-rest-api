package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
import hr.tvz.java.web.susac.joke.service.CategoryService;
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
@RequestMapping(value = "/api/joke")
public class JokeController {

    private final CategoryService categoryService;
    private final JokeService jokeService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllNewest(){
        List<JokeDTO> jokeDTOList = jokeService.findAllNewest();

        if(CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Joke list is empty!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @GetMapping("/by-category/{category}")
    public ResponseEntity<?> getAllByCategory(@PathVariable("category") String category){
        CategoryDTO categoryDTO = categoryService.findOneByName(category);

        if(Objects.isNull(categoryDTO))
            return new ResponseEntity<>("Selected Joke Category does not exists!", HttpStatus.NOT_FOUND);

        List<JokeDTO> jokeDTOList = jokeService.findAllByCategory(categoryDTO.getName());

        if (CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Selected Joke Category list is empty!", HttpStatus.NO_CONTENT);


        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @PostMapping("/by-search")
    public ResponseEntity<?> getAllBySearch(@RequestBody CategorySearchDTO categorySearchDTO){
        List<JokeDTO> jokeDTOList = jokeService.findAllByParam(categorySearchDTO);

        if(CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Joke list is empty!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @GetMapping("/by-user/{user}")
    public ResponseEntity<?> getAllByUser(@PathVariable("user") String user){
        UserDTO userDTO = userService.findOneByUsernameEnabled(user);

        if(Objects.isNull(userDTO))
            return new ResponseEntity<>("User does not exists!", HttpStatus.NOT_FOUND);

        List<JokeDTO> jokeDTOList = jokeService.findAllByUser(userDTO);

        if(CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Joke list is empty!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Integer id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(jokeDTO, HttpStatus.OK);
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
