package com.s2f.s2fapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
        ExceptionSchema exceptionResponse =
                new ExceptionSchema(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequesExceptions(BadRequestException ex) {
        ExceptionSchema exceptionResponse =
                new ExceptionSchema(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public final ResponseEntity<Object> handleInternalServerErrorExceptions(InternalServerErrorException ex) {
        ExceptionSchema exceptionResponse =
                new ExceptionSchema(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public final ResponseEntity<Object> handleMethodNotAllowedExceptions(MethodNotAllowedException ex) {
        ExceptionSchema exceptionResponse =
                new ExceptionSchema(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
