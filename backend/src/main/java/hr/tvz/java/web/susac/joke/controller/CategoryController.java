package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.CategoryDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.exception.category.CategoryNotFoundException;
import hr.tvz.java.web.susac.joke.service.CategoryService;
import hr.tvz.java.web.susac.joke.service.JokeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api(description = "Contains API operations for Joke Category model.")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final JokeService jokeService;

    @ApiOperation(value = "Retrieves Joke Category list.")
    @ApiResponse(code = 200, message = "OK", response = void.class)
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<CategoryDTO> categoryDTOList = categoryService.findAll();

        return ResponseEntity.ok(categoryDTOList);
    }

    @ApiOperation(value = "Retrieves selected Joke Category by it's given identifier, " +
            "along with jokes that are associated with selected Joke Category.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = JokeDTO.class),
            @ApiResponse(code = 400, message = "Bad Request!", response = void.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Long id){
        CategoryDTO categoryDTO = categoryService.findOneById(id);

        if (Objects.isNull(categoryDTO)) throw new CategoryNotFoundException();

        categoryDTO.setJokeDTOList(jokeService.findAllByCategory(id));

        return ResponseEntity.ok(categoryDTO);
    }

    @ApiOperation(value = "Retrieves Joke Category list by their given search params.")
    @ApiResponse(code = 200, message = "OK", response = void.class)
    @PostMapping("/search")
    public ResponseEntity<?> getAllBySearch(@RequestBody CategorySearchDTO searchDTO){
        List<CategoryDTO> categoryDTOList = categoryService.findAllByParam(searchDTO);

        return ResponseEntity.ok(categoryDTOList);
    }
}
