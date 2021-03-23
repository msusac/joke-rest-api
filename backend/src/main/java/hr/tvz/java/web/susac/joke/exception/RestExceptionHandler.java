package hr.tvz.java.web.susac.joke.exception;

import hr.tvz.java.web.susac.joke.exception.category.CategoryNotFoundException;
import hr.tvz.java.web.susac.joke.exception.joke.JokeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException() {
        return new ResponseEntity<String>("Select Joke Category does not exists!", HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JokeNotFoundException.class)
    public ResponseEntity<String> handleJokeNotFoundException() {
        return new ResponseEntity<String>("Selected Joke does not exists!", HttpStatus.BAD_REQUEST);
    }

    public static Map<String, String> handleValidationExceptions(Errors ex){
        Map<String, String> errors = new HashMap<>();

        ex.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
