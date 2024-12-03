package com.s2f.s2fapi.exceptions;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionSchema {
    private int status; // Code d'erreur HTTP
    private String message; // Message d'erreur
    private LocalDateTime timestamp; // Timestamp de l'erreur
    private String traceId; // Identifiant de trace

    // Constructeur spécifique pour simplifier la création d'instances
    public ExceptionSchema(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.traceId = UUID.randomUUID().toString(); // Génération d'un identifiant de trace unique
    }
}
