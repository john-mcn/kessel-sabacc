package com.johnm.sabacc.backend.config;

import com.johnm.sabacc.backend.dto.ApiErrorDTO;
import com.johnm.sabacc.backend.exceptions.AccessForbiddenException;
import com.johnm.sabacc.backend.exceptions.EntityAlreadyExistsException;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessForbiddenException.class)
    public ResponseEntity<ApiErrorDTO> handleNoSuchUserException(AccessForbiddenException ex) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(IllegalActionException.class)
    public ResponseEntity<ApiErrorDTO> handleNoSuchUserException(IllegalActionException ex) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ApiErrorDTO> handleNoSuchUserException(EntityAlreadyExistsException ex) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleNoSuchUserException(EntityNotFoundException ex) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleDataIntegrityViolation(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldname = error instanceof FieldError fieldError
                    ? fieldError.getField()
                    : error.getObjectName();
            errors.computeIfAbsent(fieldname, k -> new ArrayList<>())
                    .add(error.getDefaultMessage());
        });
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Some of the form values were invalid",
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

}

