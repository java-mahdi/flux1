package com.kh.userservice.advice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.AccessDeniedException;
import java.sql.SQLDataException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {
    final ObjectMapper objectMapper;

    private ResponseEntity<Mono<String>> generateResponse(HttpStatus status, String message ) {
        return ResponseEntity.status(status).body( Mono.just(message) );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Mono<String>> publicException(Exception ex){
        return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR , ex.getMessage());
    }



    @ExceptionHandler(BindException.class)
    public ResponseEntity<Mono<String>> bindException(BindException ex){
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fr -> fieldErrors.put(fr.getField() , fr.getDefaultMessage()));
        return generateResponse(HttpStatus.BAD_REQUEST , "Check fields:" + objectMapper.writeValueAsString(fieldErrors) );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, ServerWebInputException.class, IllegalArgumentException.class})
    public ResponseEntity<Mono<String>> argumentException(Exception ex){
        return generateResponse(HttpStatus.BAD_REQUEST , "check arguments." + ex.getMessage() );
    }

    @ExceptionHandler({ SQLDataException.class, TransactionalException.class, CannotCreateTransactionException.class, DuplicateKeyException.class,  })
    public ResponseEntity<Mono<String>> sqlException(Exception ex){
        return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR , "SQL error:" + ex.getMessage() );

    }



    @ExceptionHandler({ AccessDeniedException.class,  DataIntegrityViolationException.class })
    public ResponseEntity<Mono<String>> accessException(Exception ex){
        return generateResponse(HttpStatus.FORBIDDEN , "Access error:" + ex.getMessage() );
    }
    @ExceptionHandler({ MethodNotAllowedException.class })
    public ResponseEntity<Mono<String>> supportException(Exception ex){
        return generateResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE , "Support error:" + ex.getMessage() );
    }

    @ExceptionHandler(EntityNotFoundException.class
    )
    public ResponseEntity<Mono<String>> entityNotFound(EntityNotFoundException ex){
        return generateResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }



}
