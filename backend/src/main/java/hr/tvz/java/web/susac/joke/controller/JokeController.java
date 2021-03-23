package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.JokeSearchDTO;
import hr.tvz.java.web.susac.joke.exception.joke.JokeNotFoundException;
import hr.tvz.java.web.susac.joke.service.JokeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static hr.tvz.java.web.susac.joke.exception.RestExceptionHandler.handleValidationExceptions;

@Api(description = "Contains API operations for Joke model.")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/joke")
public class JokeController {

    private final JokeService jokeService;

    @ApiOperation(value = "Retrieves selected Joke by it's given identifier.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = JokeDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = void.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Long id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO)) throw new JokeNotFoundException();

        return ResponseEntity.ok(jokeDTO);
    }

    @ApiOperation(value = "Retrieves Joke list by random.")
    @ApiResponse(code = 200, message = "OK", response = void.class)
    @GetMapping("/random")
    public ResponseEntity<?> getAllByRandom(){
        List<JokeDTO> jokeDTOList = jokeService.findAllByRandom();

        return ResponseEntity.ok(jokeDTOList);
    }

    @ApiOperation(value = "Retrieves Joke list by their given search params.")
    @ApiResponse(code = 200, message = "OK", response = void.class)
    @PostMapping("/search")
    public ResponseEntity<?> getAllBySearch(@RequestBody JokeSearchDTO jokeSearchDTO){
        List<JokeDTO> jokeDTOList = jokeService.findAllByParam(jokeSearchDTO);

        return ResponseEntity.ok(jokeDTOList);
    }

    @ApiOperation(value = "Created a new Joke.",
        notes = "If selected Joke Category title does not exists in database, server will create it.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = JokeDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = void.class)
    })
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody JokeDTO jokeDTO, @ApiIgnore Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.badRequest().body(handleValidationExceptions(errors));

        JokeDTO result = jokeService.save(jokeDTO);

        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Updates selected Joke by it's given identifier.",
            notes = "If selected Joke Category title does not exists in database, server will create it.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = JokeDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = void.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @Valid @RequestBody JokeDTO updateDTO,
                                        @ApiIgnore Errors errors){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO)) throw new JokeNotFoundException();

        if(errors.hasErrors())
            return ResponseEntity.badRequest().body(handleValidationExceptions(errors));


        updateDTO.setId(id);
        JokeDTO result = jokeService.save(updateDTO);

        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Deleted selected Joke by it's given identifier.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = JokeDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = void.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO)) throw new JokeNotFoundException();

        jokeService.deleteById(id);

        String name = jokeDTO.getCategoryTitle();

        return ResponseEntity.ok("The" + " " + name + " #" + id + " " + "joke was deleted!");
    }
}
