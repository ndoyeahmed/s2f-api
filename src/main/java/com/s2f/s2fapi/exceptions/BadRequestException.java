package com.s2f.s2fapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@Setter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {
    private String message;
}
