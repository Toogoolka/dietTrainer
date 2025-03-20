package ru._1221systems.trainer.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> invalidFieldsHandleException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError err: fieldErrors) {
            errors.put(err.getField(), err.getDefaultMessage());
        }

        return new ResponseEntity<>(ErrorResponse.builder()
                .msg("INVALID DATA")
                .details(errors.toString())
                .status("BAD REQUEST")
                .build(), HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> badRequestHandle(RuntimeException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .msg("UNRECOGNIZED ERROR")
                .details(e.getMessage())
                .status("BAD REQUEST")
                .build(), HttpStatus.BAD_REQUEST);
    }
}
