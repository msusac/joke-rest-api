package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.search.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.service.CategoryService;
import hr.tvz.java.web.susac.joke.service.JokeService;
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
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/category")
@Api(description = "Contains API operations for Joke Category model.")
public class CategoryController {

    private final CategoryService categoryService;
    private final JokeService jokeService;

    @ApiOperation(value = "Retrieves Joke Category list.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke Category list]", response = void.class),
            @ApiResponse(code = 204, message = "Joke Category list is empty!", response = void.class)
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CategoryDTO> categoryDTOList = categoryService.findAllNameAsc();

        if (CollectionUtils.isEmpty(categoryDTOList))
            return new ResponseEntity<>("Joke Category list is empty!", HttpStatus.NO_CONTENT);


        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Joke Category list by it's given param.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke Category list]", response = void.class),
            @ApiResponse(code = 204, message = "Joke Category list is empty!", response = void.class),
            @ApiResponse(code = 406, message = "[Failed validation]", response = void.class)
    })
    @PostMapping("/by-search")
    public ResponseEntity<?> getAllBySearch(@Valid @RequestBody CategorySearchDTO categorySearchDTO,
                                            @ApiIgnore Errors errors){
        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);

        List<CategoryDTO> categoryDTOList = categoryService.findAllByNameLikeAsc(categorySearchDTO);

        if (CollectionUtils.isEmpty(categoryDTOList))
            return new ResponseEntity<>("Joke Category list is empty!", HttpStatus.NO_CONTENT);


        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Joke Category by it's unique identifier.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Joke Category]", response = CategoryDTO.class),
            @ApiResponse(code = 404, message = "Selected Joke Category does not exists!", response = void.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Integer id){
        CategoryDTO categoryDTO = categoryService.findOneById(id);

        if(Objects.isNull(categoryDTO))
            return new ResponseEntity<>("Selected Joke Category does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Joke Category details by it's unique identifier.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays Jokes list]", response = void.class),
            @ApiResponse(code = 204, message = "Selected Joke Category list is empty!", response = void.class),
            @ApiResponse(code = 404, message = "Selected Joke Category does not exists!", response = void.class)
    })
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getOneByIdDetails(@PathVariable Integer id){
        CategoryDTO categoryDTO = categoryService.findOneById(id);

        if(Objects.isNull(categoryDTO))
            return new ResponseEntity<>("Selected Joke Category does not exists!", HttpStatus.NOT_FOUND);

        List<JokeDTO> jokeDTOList = jokeService.findAllByCategory(categoryDTO.getName());

        if (CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Selected Joke Category list is empty!", HttpStatus.NO_CONTENT);


        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Allows Joke Category creation. (ADMIN ONLY!)",
            authorizations = { @Authorization(value="jwtToken")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "[Displays created Joke Category]", response = CategoryDTO.class),
            @ApiResponse(code = 403, message = "[User is not logged as Admin]", response = void.class),
            @ApiResponse(code = 406, message = "[Failed validation]\n" +
                    "[Joke Category name already taken]\n" +
                    "[Error has occurred during operation]",
                    response = void.class)
    })
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CategoryDTO categoryDTO,
                                  @ApiIgnore Errors errors){
        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);

        if(!Objects.isNull(categoryService.findOneByName(categoryDTO.getName())))
            return new ResponseEntity<>("Joke Category" + " " + categoryDTO.getName() + " " + "already exists!",
                    HttpStatus.NOT_ACCEPTABLE);

        try{
            categoryDTO = categoryService.save(categoryDTO);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        categoryDTO = categoryService.findOneByName(categoryDTO.getName());
        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Allows updating selected Joke Category by it's given unique identifier. (ADMIN ONLY!)",
            authorizations = { @Authorization(value="jwtToken")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays updated Joke Category]", response = CategoryDTO.class),
            @ApiResponse(code = 403, message = "[User is not logged as Admin]", response = void.class),
            @ApiResponse(code = 404, message = "Selected Joke Category does not exists!", response = void.class),
            @ApiResponse(code = 406, message = "[Failed validation]\n" +
                    "[Joke Category name already taken]\n" +
                    "[Error has occurred during operation]", response = void.class)
    })
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id,
                                        @Valid @RequestBody CategoryDTO updateDTO,
                                        @ApiIgnore Errors errors){
        CategoryDTO categoryDTO = categoryService.findOneById(id);

        if(Objects.isNull(categoryDTO))
            return new ResponseEntity<>("Selected Joke Category does not exists!", HttpStatus.NOT_FOUND);

        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);


        if(!Objects.isNull(categoryService.findOneByName(updateDTO.getName())) &&
                (!updateDTO.getName().equals(categoryDTO.getName())))
            return new ResponseEntity<>("Selected Joke Category name" + " " + updateDTO.getName() + " " + "already taken!",
                    HttpStatus.NOT_ACCEPTABLE);

        try{
            updateDTO.setId(id);
            categoryService.save(updateDTO);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        updateDTO = categoryService.findOneById(id);
        return new ResponseEntity<>(updateDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Allows deleting selected Joke Category by it's given unique identifier. (ADMIN ONLY!)",
            authorizations = { @Authorization(value="jwtToken")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Joke category (name) was deleted!", response = void.class),
            @ApiResponse(code = 403, message = "[User is not logged as Admin]", response = void.class),
            @ApiResponse(code = 404, message = "Selected Joke Category does not exists!", response = void.class),
            @ApiResponse(code = 406, message = "[Error has occurred during operation]", response = void.class)
    })
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        CategoryDTO categoryDTO = categoryService.findOneById(id);

        if(Objects.isNull(categoryDTO))
            return new ResponseEntity<>("Selected Joke Category does not exists!", HttpStatus.NOT_FOUND);

        try{
            categoryService.deleteById(id);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("Joke Category" + " " + categoryDTO.getName() + " " + "was deleted!",
                HttpStatus.OK
        );
    }
}
