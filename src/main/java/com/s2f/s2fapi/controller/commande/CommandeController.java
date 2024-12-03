package com.s2f.s2fapi.controller.commande;

import com.s2f.s2fapi.service.commande.interfaces.CommandeService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommandeController {
    private final CommandeService commandeService;

    @GetMapping("/v1/commande-number")
    public ResponseEntity<?> getNewCommandeNumber() {
        return ResponseEntity.ok(
                Collections.singletonMap("response", commandeService.getNextCommandeNumber()));
    }
}
