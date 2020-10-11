package hr.tvz.java.web.susac.joke.util.validation;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

@AllArgsConstructor
public class ValidationErrorPrinter {

    public static ResponseEntity<?> showValidationError(Errors errors){
        String error = "";
        List<FieldError> errorsList = errors.getFieldErrors();

        for(int i = 0; i < errorsList.size(); i++){
            String fieldError = errorsList.get(i).getDefaultMessage() + "\n";
            error += fieldError;
        }

        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }
}
