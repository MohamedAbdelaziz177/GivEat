package com.abdelaziz26.cishackit.Core.Exceptions;

import com.abdelaziz26.cishackit.Core.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach((error) -> {
            var key = error.getField();
            var value = error.getDefaultMessage();
            errors.put(key, value);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex){

        ApiResponse<Void> res = logErrorAndCreateFailureResponse(ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundException(EntityNotFoundException ex){

        ApiResponse<Void> res = logErrorAndCreateFailureResponse(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException ex)
    {
        ApiResponse<Void> res = logErrorAndCreateFailureResponse(ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex){

        ApiResponse<Void> res = logErrorAndCreateFailureResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoSuchElementException(NoSuchElementException ex){

        ApiResponse<Void> res = logErrorAndCreateFailureResponse(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse<Void>> handleIOException(IOException ex){

        ApiResponse<Void> res = logErrorAndCreateFailureResponse(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationServiceException(AuthenticationServiceException ex){
        ApiResponse<Void> res = logErrorAndCreateFailureResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    //@ExceptionHandler(StripeException.class)
    //public ResponseEntity<ApiResponse<Void>> handleStripeException(StripeException ex){
    //    ApiResponse<Void> res = logErrorAndCreateFailureResponse(ex);
    //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    //}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex){

        ApiResponse<Void> res = logErrorAndCreateFailureResponse(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }


    private ApiResponse<Void> logErrorAndCreateFailureResponse(Exception ex){

        logger.error("error occurred", ex);

        ApiResponse<Void> res = new ApiResponse<>();

        res.setSuccess(Boolean.FALSE);
        res.setMessage(ex.getMessage());

        return res;
    }
}
