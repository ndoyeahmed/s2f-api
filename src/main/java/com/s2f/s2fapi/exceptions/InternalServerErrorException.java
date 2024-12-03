package com.s2f.s2fapi.exceptions;

import com.s2f.s2fapi.constants.ErrorsMessages;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
@Setter
public class InternalServerErrorException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1L; // Ajout d'un identifiant de version

    private String message;

    // Constructeur avec message
    public InternalServerErrorException(String message) {
        super(message); // Passer le message à la classe parent
        this.message = message;
    }

    // Constructeur par défaut pour un message générique
    public InternalServerErrorException() {
        super(ErrorsMessages.INTERNAL_SERVER_ERROR_MESSAGE); // Message par défaut
        this.message = ErrorsMessages.INTERNAL_SERVER_ERROR_MESSAGE;
    }
}
