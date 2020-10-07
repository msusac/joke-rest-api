package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.service.JokeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/joke")
public class JokeController {

    private JokeService jokeService;

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

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody JokeDTO jokeDTO, Errors errors){
        if(errors.hasErrors()){
            String error = "";
            List<FieldError> errorsList = errors.getFieldErrors();

            for(int i = 0; i < errorsList.size(); i++){
                String fieldError = errorsList.get(i).getDefaultMessage() + "\n";
                error += fieldError;
            }

            return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }

        try{
            jokeDTO = jokeService.save(jokeDTO);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(jokeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @Valid @RequestBody JokeDTO updateDTO, Errors errors){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        if(errors.hasErrors()){
            String error = "";
            List<FieldError> errorsList = errors.getFieldErrors();

            for(int i = 0; i < errorsList.size(); i++){
                String fieldError = errorsList.get(i).getDefaultMessage() + "\n";
                error += fieldError;
            }

            return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }

        try{
            updateDTO.setId(id);
            jokeService.save(updateDTO);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        updateDTO = jokeService.findOneById(id);
        return new ResponseEntity<>(updateDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        try{
            jokeService.deleteById(id);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("The following" + " " + jokeDTO.getCategory() + " " + "joke was deleted!\n"
                + jokeDTO.getDescription(), HttpStatus.OK
        );
    }
}
