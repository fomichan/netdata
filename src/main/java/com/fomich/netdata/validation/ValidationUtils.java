package com.fomich.netdata.validation;

import com.fomich.netdata.dto.ValidationErrorDTO;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ValidationUtils {



    // Вариант возврата результата валидации (возвращает ResponseEntity<?>)
    public ResponseEntity<ValidationErrorDTO> prepareBadRequestResponse(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        for (ObjectError error : bindingResult.getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }

        ValidationErrorDTO errorDTO = new ValidationErrorDTO(errors);
        return ResponseEntity.badRequest().body(errorDTO);
    }
}
