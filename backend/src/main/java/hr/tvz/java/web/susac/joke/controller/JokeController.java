package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.search.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
import hr.tvz.java.web.susac.joke.service.CategoryService;
import hr.tvz.java.web.susac.joke.service.JokeService;
import hr.tvz.java.web.susac.joke.service.UserService;
import hr.tvz.java.web.susac.joke.util.validation.ValidationErrorPrinter;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/joke")
@Api(description = "Contains API operations for Joke model.")
public class JokeController {

    private final CategoryService categoryService;
    private final JokeService jokeService;
    private final UserService userService;

    @ApiOperation(value = "Retrieves newest Jokes.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke list]", response = void.class),
            @ApiResponse(code = 204, message = "Joke list is empty!", response = void.class)
    })
    @GetMapping
    public ResponseEntity<?> getAllNewest(){
        List<JokeDTO> jokeDTOList = jokeService.findAllNewest();

        if(CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Joke list is empty!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Joke list by it's Joke Category.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke list]", response = void.class),
            @ApiResponse(code = 204, message = "Joke list is empty!", response = void.class),
            @ApiResponse(code = 404, message = "Selected Joke Category does not exists!", response = void.class)
    })
    @GetMapping("/by-category/{category}")
    public ResponseEntity<?> getAllByCategory(@PathVariable("category") String category){
        CategoryDTO categoryDTO = categoryService.findOneByName(category);

        if(Objects.isNull(categoryDTO))
            return new ResponseEntity<>("Selected Joke Category does not exists!", HttpStatus.NOT_FOUND);

        List<JokeDTO> jokeDTOList = jokeService.findAllByCategory(categoryDTO.getName());

        if (CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Joke list is empty!", HttpStatus.NO_CONTENT);


        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Joke list by it's given param.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke list]", response = void.class),
            @ApiResponse(code = 204, message = "Joke list is empty!", response = void.class),
            @ApiResponse(code = 406, message = "[Failed validation]", response = void.class)
    })
    @PostMapping("/by-search")
    public ResponseEntity<?> getAllBySearch(@Valid @RequestBody CategorySearchDTO categorySearchDTO,
                                            @ApiIgnore Errors errors){
        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);

        List<JokeDTO> jokeDTOList = jokeService.findAllByParam(categorySearchDTO);

        if(CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Joke list is empty!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Jokes by it's User.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke list]", response = void.class),
            @ApiResponse(code = 204, message = "Joke list is empty!", response = void.class),
            @ApiResponse(code = 404, message = "Selected User does not exists!", response = void.class)
    })
    @GetMapping("/by-user/{user}")
    public ResponseEntity<?> getAllByUser(@PathVariable("user") String user){
        UserDTO userDTO = userService.findOneByUsernameEnabled(user);

        if(Objects.isNull(userDTO))
            return new ResponseEntity<>("Selected User does not exists!", HttpStatus.NOT_FOUND);

        List<JokeDTO> jokeDTOList = jokeService.findAllByUser(userDTO);

        if(CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Joke list is empty!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Joke by it's unique identifier.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke]", response = CategoryDTO.class),
            @ApiResponse(code = 404, message = "Selected Joke does not exists!", response = void.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Integer id){
        JokeDTO jokeDTO = jokeService.findOneById(id);

        if(Objects.isNull(jokeDTO))
            return new ResponseEntity<>("Selected Joke does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(jokeDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Allows Joke creation. (ADMIN OR USER ONLY!)",
            notes = "If selected Joke Category does not exists in database, the app will create it",
            authorizations = { @Authorization(value="jwtToken")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "[Displays created Joke]", response = CategoryDTO.class),
            @ApiResponse(code = 403, message = "[User is not logged as Admin or User]", response = void.class),
            @ApiResponse(code = 406, message = "[Failed validation]\n" +
                    "[Error has occurred during operation]", response = void.class)
    })
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody JokeDTO jokeDTO,
                                  @ApiIgnore Errors errors,
                                  Principal principal){
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

    @ApiOperation(value = "Allows updating selected Joke by it's given unique identifier. (ADMIN ONLY!)",
            notes = "If selected Joke Category does not exists in database, the app will create it",
            authorizations = { @Authorization(value="jwtToken")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays updated Joke]", response = CategoryDTO.class),
            @ApiResponse(code = 403, message = "[User is not logged as Admin]", response = void.class),
            @ApiResponse(code = 404, message = "Selected Joke does not exists!", response = void.class),
            @ApiResponse(code = 406, message = "[Failed validation]\n" +
                    "[Error has occurred during operation]", response = void.class)
    })
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id,
                                        @Valid @RequestBody JokeDTO updateDTO,
                                        @ApiIgnore Errors errors){
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

    @ApiOperation(value = "Allows deleting selected Joke by it's given unique identifier. (ADMIN ONLY!)",
            authorizations = { @Authorization(value="jwtToken")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "The following (name) joke was deleted! (joke description)", response = void.class),
            @ApiResponse(code = 403, message = "[User is not logged as Admin]", response = void.class),
            @ApiResponse(code = 404, message = "Selected Joke does not exists!", response = void.class),
            @ApiResponse(code = 406, message = "[Error has occurred during operation]", response = void.class)
    })
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
