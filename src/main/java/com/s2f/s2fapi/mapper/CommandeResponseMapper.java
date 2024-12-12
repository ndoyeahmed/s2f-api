package com.s2f.s2fapi.mapper;

import com.s2f.s2fapi.dto.response.CommandeDtoResponse;
import com.s2f.s2fapi.model.Commande;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandeResponseMapper {
    private final ClientMapper clientMapper;

    public CommandeDtoResponse toCommandeDtoResponse(Commande savedCommande) {
        return CommandeDtoResponse.builder()
                .id(savedCommande.getId())
                .date(savedCommande.getDate())
                .numero(savedCommande.getNumero())
                .etatCommande(savedCommande.getEtatCommande())
                .client(clientMapper.toClientDTOResponse(savedCommande.getClient()))
                .build();
    }
}
