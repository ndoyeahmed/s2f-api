package com.s2f.s2fapi.dto.request;

import com.s2f.s2fapi.model.EtatCommande;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommandeDtoRequest {
    private Date date;
    private String numero;
    private EtatCommande etatCommande = EtatCommande.ENCOURS;
    private Long clientId;
    private List<CommandeProduitDtoRequest> orderedProducts;
}
