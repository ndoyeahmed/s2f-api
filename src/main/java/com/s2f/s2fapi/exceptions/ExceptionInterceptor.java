package com.s2f.s2fapi.exceptions;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Access Denied: " + ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ExceptionSchema> handleEntityNotFoundExceptions(
            EntityNotFoundException ex) {
        ExceptionSchema exceptionResponse =
                new ExceptionSchema(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        LocalDateTime.now(),
                        UUID.randomUUID().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionSchema> handleBadRequestExceptions(
            BadRequestException ex) {
        ExceptionSchema exceptionResponse =
                new ExceptionSchema(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        LocalDateTime.now(),
                        UUID.randomUUID().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public final ResponseEntity<ExceptionSchema> handleInternalServerErrorExceptions(
            InternalServerErrorException ex) {
        ExceptionSchema exceptionResponse =
                new ExceptionSchema(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage(),
                        LocalDateTime.now(),
                        UUID.randomUUID().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public final ResponseEntity<ExceptionSchema> handleMethodNotAllowedExceptions(
            MethodNotAllowedException ex) {
        ExceptionSchema exceptionResponse =
                new ExceptionSchema(
                        HttpStatus.METHOD_NOT_ALLOWED.value(),
                        ex.getMessage(),
                        LocalDateTime.now(),
                        UUID.randomUUID().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // Gestionnaire d'exception générique
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionSchema> handleAllExceptions(Exception ex) {
        ExceptionSchema exceptionResponse =
                new ExceptionSchema(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "An unexpected error occurred: " + ex.getMessage(),
                        LocalDateTime.now(),
                        UUID.randomUUID().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
