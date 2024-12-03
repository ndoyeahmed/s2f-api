package com.s2f.s2fapi.dto.request;

import com.s2f.s2fapi.dto.response.ProduitDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommandeProduitDtoRequest {
    private double quantite;
    private double prixVente;
    private long produitId;
}
