package com.kh.web.advice;


import com.kh.web.application.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebInputException;
import tools.jackson.databind.ObjectMapper;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLDataException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    final ObjectMapper objectMapper;
    private ResponseEntity<LinkedHashMap> generateResponse(HttpStatus status, String message ) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("", "something went wrong!");
        map.put("time", new Date().toString());
        map.put("reason", status.getReasonPhrase());
        map.put("", message);
        return ResponseEntity.status(status).body(map);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<LinkedHashMap> publicException(Exception ex){
        return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR , ex.getMessage());
    }
  @ExceptionHandler(CustomException.class)
    public ResponseEntity<LinkedHashMap> CustomException(CustomException ex){
        return generateResponse(HttpStatus.valueOf(ex.getStatus()) , ex.getMessage());
    }

    @ExceptionHandler({ UsernameNotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<LinkedHashMap> notFoundException(Exception ex){
        return generateResponse(HttpStatus.NOT_FOUND , "not found:" + ex.getMessage() );
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<LinkedHashMap> bindException(BindException ex){
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fr -> fieldErrors.put(fr.getField() , fr.getDefaultMessage()));
        return generateResponse(HttpStatus.BAD_REQUEST , "Check fields:" + objectMapper.writeValueAsString(fieldErrors) );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, ServerWebInputException.class, IllegalArgumentException.class})
    public ResponseEntity<LinkedHashMap> argumentException(Exception ex){
        return generateResponse(HttpStatus.BAD_REQUEST , "check arguments." + ex.getMessage() );
    }



    @ExceptionHandler({  AuthenticationException.class, BadCredentialsException.class })
    public ResponseEntity<LinkedHashMap> loginException(Exception ex){
        return generateResponse(HttpStatus.NOT_ACCEPTABLE , "Login error:" + ex.getMessage() );
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<LinkedHashMap> accessException(Exception ex){
        return generateResponse(HttpStatus.FORBIDDEN , "Access error:" + ex.getMessage() );
    }
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class })
    public ResponseEntity<LinkedHashMap> supportException(Exception ex){
        return generateResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE , "Support error:" + ex.getMessage() );
    }


}
