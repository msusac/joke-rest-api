package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.service.CategoryService;
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
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final JokeService jokeService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CategoryDTO> categoryDTOList = categoryService.findAllNameAsc();

        if (CollectionUtils.isEmpty(categoryDTOList))
            return new ResponseEntity<>("Joke Category list is empty!", HttpStatus.NOT_FOUND);


        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Integer id){
        CategoryDTO categoryDTO = categoryService.findOneById(id);

        if(Objects.isNull(categoryDTO))
            return new ResponseEntity<>("Selected Joke Category does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> getOneByIdDetails(@PathVariable Integer id){
        CategoryDTO categoryDTO = categoryService.findOneById(id);

        if(Objects.isNull(categoryDTO))
            return new ResponseEntity<>("Selected Joke Category does not exists!", HttpStatus.NOT_FOUND);

        List<JokeDTO> jokeDTOList = jokeService.findAllByCategory(categoryDTO.getName());

        if (CollectionUtils.isEmpty(jokeDTOList))
            return new ResponseEntity<>("Selected Joke Category list is empty!", HttpStatus.NOT_FOUND);


        return new ResponseEntity<>(jokeDTOList, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CategoryDTO categoryDTO, Errors errors){
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

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @Valid @RequestBody CategoryDTO updateDTO, Errors errors){
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
