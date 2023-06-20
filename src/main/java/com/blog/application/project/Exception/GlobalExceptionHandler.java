package com.blog.application.project.Exception;

import com.blog.application.project.Payload.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseObject> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        ResponseObject responseObject = new ResponseObject();

        responseObject.setMessage(exception.getMessage());
        responseObject.setSuccess(false);
        responseObject.setData(null);

        return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> response = new HashMap<>();

        exception.getBindingResult().getAllErrors().stream().forEach((error)-> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            response.put(fieldName,message);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ResponseObject> apiExceptionHandler(ApiException exception) {
        ResponseObject responseObject = new ResponseObject();

        responseObject.setMessage(exception.getMessage());
        responseObject.setSuccess(false);

        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }
}
