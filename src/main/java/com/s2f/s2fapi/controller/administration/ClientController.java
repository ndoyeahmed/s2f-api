package com.s2f.s2fapi.controller.administration;

import com.s2f.s2fapi.dto.request.ClientDtoRequest;
import com.s2f.s2fapi.dto.response.ClientDtoResponse;
import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.service.administration.interfaces.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/v1/clients")
    public ResponseEntity<ClientDtoResponse> addNewClient(
            @RequestBody @Valid ClientDtoRequest clientDtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.addClient(clientDtoRequest));
    }

    @GetMapping("/v1/clients")
    public ResponseEntity<List<ClientDtoResponse>> getAllClientNotArchive() {
        return ResponseEntity.ok(clientService.getAllClientNotArchive());
    }

    @GetMapping("/v1/clients/filter")
    public ResponseEntity<ResponseDTOPaging<ClientDtoResponse>> getAllClientByFilters(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String telephone,
            Pageable pageable) {
        return ResponseEntity.ok(clientService.filterClients(nom, telephone, pageable));
    }
}
