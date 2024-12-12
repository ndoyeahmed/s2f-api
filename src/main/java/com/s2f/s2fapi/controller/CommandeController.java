package com.s2f.s2fapi.controller;

import com.s2f.s2fapi.dto.request.CommandeDtoRequest;
import com.s2f.s2fapi.dto.response.CommandeDtoResponse;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.service.interfaces.CommandeService;
import java.util.Collections;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class CommandeController {
    private final CommandeService commandeService;

    @GetMapping("/v1/commande-number")
    public ResponseEntity<?> getNewCommandeNumber() {
        return ResponseEntity.ok(
                Collections.singletonMap("response", commandeService.getNextCommandeNumber()));
    }

    @GetMapping("/v1/commandes")
    public ResponseEntity<ResponseDTOPaging<CommandeDtoResponse>> getAllCommandeByEtatCommande(@RequestParam(name = "etat") String etatCommande, Pageable pageable) {
        return ResponseEntity.ok(commandeService.getAllCommandeByEtatCommande(etatCommande, pageable));
    }

    /**
     * Endpoint pour créer une nouvelle commande.
     *
     * @param commandeDtoRequest La requête contenant les informations de la commande.
     * @return La réponse contenant les détails de la commande créée.
     */
    @PostMapping("/v1/commandes")
    public ResponseEntity<CommandeDtoResponse> addNewCommande(
            @Valid @RequestBody CommandeDtoRequest commandeDtoRequest) {
        var response = commandeService.addNewCommande(commandeDtoRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
